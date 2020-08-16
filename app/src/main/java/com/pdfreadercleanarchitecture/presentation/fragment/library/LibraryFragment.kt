package com.pdfreadercleanarchitecture.presentation.fragment.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.pdfreadercleanarchitecture.R
import com.pdfreadercleanarchitecture.framework.PdfReaderApplication
import com.pdfreadercleanarchitecture.framework.PdfReaderViewModelFactory
import com.pdfreadercleanarchitecture.presentation.activity.MainActivity
import com.pdfreadercleanarchitecture.presentation.fragment.adapter.DocumentAdapter
import com.pdfreadercleanarchitecture.presentation.util.IntentUtil.createOpenIntent
import com.pdfreadercleanarchitecture.presentation.util.MainActivityDelegate
import kotlinx.android.synthetic.main.library_fragment.*

class LibraryFragment : Fragment() {

    companion object {
        const val READ_REQUEST_CODE = 100

        fun newInstance() = LibraryFragment()
    }

    private lateinit var viewModel: LibraryViewModel

    private lateinit var mainActivityDelegate: MainActivityDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try{
            mainActivityDelegate = context as MainActivityDelegate
        }catch (e :ClassCastException){
            throw ClassCastException()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.library_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = DocumentAdapter(glide = Glide.with(this)){
            mainActivityDelegate.openDocument(it)
        }
        documentsRecyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this , PdfReaderViewModelFactory)
            .get(LibraryViewModel::class.java)
        viewModel.document.observe(viewLifecycleOwner , Observer{
            adapter.update(it)
        })
        viewModel.loadDocuments()
        fab.setOnClickListener{
            startActivityForResult(createOpenIntent() , READ_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.data?.also { uri -> viewModel.addDocument(uri) }
            }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
