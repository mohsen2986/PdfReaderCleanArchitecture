package com.pdfreadercleanarchitecture.presentation.fragment.reader

import android.app.Application
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import com.example.core.domain.Document
import androidx.lifecycle.*
import com.example.core.domain.Bookmark
import com.pdfreadercleanarchitecture.framework.Interactors
import com.pdfreadercleanarchitecture.framework.PdfReaderViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.core.os.bundleOf

class ReaderViewModel(
    application: Application, interactors: Interactors
) : PdfReaderViewModel(application , interactors) {



    companion object {
        private const val DOCUMENT_ARG = "document"

        fun createArguments(document: Document) =  bundleOf(
            DOCUMENT_ARG to document
        )

    }

    val document = MutableLiveData<Document>()

    val bookmarks = MediatorLiveData<List<Bookmark>>().apply {
        addSource(document) { document ->
            GlobalScope.launch {
                postValue(interactors.getBookmarks(document))
            }
        }
    }

    val currentPage = MediatorLiveData<PdfRenderer.Page>()

    val hasPreviousPage: LiveData<Boolean> = Transformations.map(currentPage) {
        it.index > 0
    }

    val hasNextPage: LiveData<Boolean> = Transformations.map(currentPage) {
        renderer.value?.let { renderer -> it.index < renderer.pageCount - 1 }
    }

    val isBookmarked = MediatorLiveData<Boolean>().apply {
        addSource(document) { value = isCurrentPageBookmarked() }
        addSource(currentPage) { value = isCurrentPageBookmarked() }
        addSource(bookmarks) { value = isCurrentPageBookmarked() }
    }

    val isInLibrary: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(document) {
            GlobalScope.launch { postValue(isInLibrary(it)) }
        }
    }

    val renderer = MediatorLiveData<PdfRenderer>().apply {
        addSource(document) {
            try {
                val pdfRenderer = getFileDescriptor(Uri.parse(it.url))?.let { it1 -> PdfRenderer(it1) }
                value = pdfRenderer
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getFileDescriptor(uri: Uri) = application.contentResolver.openFileDescriptor(uri, "r")

    private fun isCurrentPageBookmarked() =
        bookmarks.value?.any { it.page == currentPage.value?.index } == true

    private suspend fun isInLibrary(document: Document) =
        interactors.getDocuments().any { it.url == document.url }

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        currentPage.apply {
            addSource(renderer) { renderer ->
                GlobalScope.launch {
                    val document = document.value

                    if (document != null) {
                        val bookmarks = interactors.getBookmarks(document).lastOrNull()?.page ?: 0
                        postValue(renderer.openPage(bookmarks))
                    }
                }
            }

            // 2
            val documentFromArguments = arguments.get(DOCUMENT_ARG) as Document? ?: Document.EMPTY

            // 3
            val lastOpenDocument = interactors.getOpenDocument()

            // 4
            document.value = when {
                documentFromArguments != Document.EMPTY -> documentFromArguments
                documentFromArguments == Document.EMPTY && lastOpenDocument != Document.EMPTY -> lastOpenDocument
                else -> Document.EMPTY
            }

            // 5
            document.value?.let { interactors.setOpenDocument(it) }
        }
    }

    fun openDocument(uri: Uri) {
        document.value = Document(uri.toString(), "", 0, "")
        document.value?.let { interactors.setOpenDocument(it) }
    }

    fun openBookmark(bookmark: Bookmark) {
        openPage(bookmark.page)
    }

    private fun openPage(page: Int) = renderer.value?.let {
        currentPage.value = it.openPage(page)
    }

    fun nextPage() = currentPage.value?.let { openPage(it.index.plus(1)) }

    fun previousPage() = currentPage.value?.let { openPage(it.index.minus(1)) }

    fun reopenPage() = openPage(currentPage.value?.index ?: 0)

    fun toggleBookmark() {
        val currentPage = currentPage.value?.index ?: return
        val document = document.value ?: return
        val bookmark = bookmarks.value?.firstOrNull { it.page == currentPage }

        GlobalScope.launch {
            if (bookmark == null) {
                interactors.addBookmark(document, Bookmark(page = currentPage))
            } else {
                interactors.deleteBookmark(document, bookmark)
            }

            bookmarks.postValue(interactors.getBookmarks(document))
        }
    }

    fun toggleInLibrary() {
        val document = document.value ?: return

        GlobalScope.launch {
            if (isInLibrary.value == true) {
                interactors.removeDocument(document)
            } else {
                interactors.addDocument(document)
            }

            isInLibrary.postValue(isInLibrary(document))
        }
    }
}
