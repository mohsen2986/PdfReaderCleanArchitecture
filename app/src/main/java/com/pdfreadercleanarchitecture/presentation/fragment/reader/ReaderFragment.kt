package com.pdfreadercleanarchitecture.presentation.fragment.reader

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfRenderer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.core.domain.Document

import com.pdfreadercleanarchitecture.R
import com.pdfreadercleanarchitecture.framework.PdfReaderApplication
import com.pdfreadercleanarchitecture.framework.PdfReaderViewModelFactory
import com.pdfreadercleanarchitecture.presentation.fragment.adapter.BookmarkAdapter
import com.pdfreadercleanarchitecture.presentation.fragment.library.LibraryFragment
import com.pdfreadercleanarchitecture.presentation.fragment.library.LibraryViewModel
import com.pdfreadercleanarchitecture.presentation.util.IntentUtil
import kotlinx.android.synthetic.main.reader_fragment.*

class ReaderFragment : Fragment() {

    companion object {
        fun newInstance(document: Document) = ReaderFragment().apply {
            arguments = ReaderViewModel.createArguments(document)
        }
    }

    private lateinit var viewModel: ReaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reader_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this , PdfReaderViewModelFactory)
            .get(ReaderViewModel::class.java)

        val adapter = BookmarkAdapter {
            viewModel.openBookmark(it)
        }
        bookmarksRecyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this , PdfReaderViewModelFactory)
            .get(ReaderViewModel::class.java)

        viewModel.document.observe(viewLifecycleOwner, Observer {
            if (it == Document.EMPTY) {
                // Show file picker action.
                startActivityForResult(IntentUtil.createOpenIntent(), LibraryFragment.READ_REQUEST_CODE)
            }
        })

        viewModel.bookmarks.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        viewModel.isBookmarked.observe(viewLifecycleOwner, Observer {
            val bookmarkDrawable = if (it) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            tabBookmark.setCompoundDrawablesWithIntrinsicBounds(0, bookmarkDrawable, 0, 0)
        })

        viewModel.isInLibrary.observe(viewLifecycleOwner, Observer {
            val libraryDrawable = if(it) R.drawable.ic_library else R.drawable.ic_library_border
            tabLibrary.setCompoundDrawablesRelativeWithIntrinsicBounds(0, libraryDrawable, 0, 0)
        })

        viewModel.currentPage.observe(viewLifecycleOwner, Observer { showPage(it) })
        viewModel.hasNextPage.observe(viewLifecycleOwner, Observer { tabNextPage.isEnabled = it })
        viewModel.hasPreviousPage.observe(viewLifecycleOwner, Observer { tabPreviousPage.isEnabled = it })

        if(savedInstanceState == null) {
            viewModel.loadArguments(arguments)
        } else {
            // Recreating fragment after configuration change, reopen current page so it can be rendered again.
            viewModel.reopenPage()
        }

        tabBookmark.setOnClickListener { viewModel.toggleBookmark() }
        tabLibrary.setOnClickListener { viewModel.toggleInLibrary() }
        tabNextPage.setOnClickListener { viewModel.nextPage() }
        tabPreviousPage.setOnClickListener { viewModel.previousPage() }
    }
    private fun showPage(page: PdfRenderer.Page) {
        iv_page.visibility = View.VISIBLE
        pagesTextView.visibility = View.VISIBLE
        tabPreviousPage.visibility = View.VISIBLE
        tabNextPage.visibility = View.VISIBLE

        if (iv_page.drawable != null) {
            (iv_page.drawable as BitmapDrawable).bitmap.recycle()
        }

        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)

        val pageWidth = size.x
        val pageHeight = page.height * pageWidth / page.width

        val bitmap = Bitmap.createBitmap(
            pageWidth,
            pageHeight,
            Bitmap.Config.ARGB_8888)

        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        iv_page.setImageBitmap(bitmap)

        pagesTextView.text = getString(
            R.string.page_navigation_format,
            page.index + 1,
            viewModel.renderer.value?.pageCount
        )

        page.close()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Process open file intent.
        if (requestCode == LibraryFragment.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri -> viewModel.openDocument(uri) }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
