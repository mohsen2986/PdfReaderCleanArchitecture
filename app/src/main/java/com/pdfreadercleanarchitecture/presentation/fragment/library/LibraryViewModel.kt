package com.pdfreadercleanarchitecture.presentation.fragment.library

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.Document
import com.pdfreadercleanarchitecture.framework.Interactors
import com.pdfreadercleanarchitecture.framework.PdfReaderApplication
import com.pdfreadercleanarchitecture.framework.PdfReaderViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryViewModel(
    application: Application, interactors: Interactors
    ) : PdfReaderViewModel(application , interactors) {

    val document: MutableLiveData<List<Document>> = MutableLiveData()

    fun loadDocuments(){
        GlobalScope.launch {
            document.postValue(interactors.getDocuments())
        }
    }
    fun addDocument(uri: Uri){
        GlobalScope.launch {
            withContext(IO){
                interactors.addDocument(Document(uri.toString() , "" , 0 , ""))
            }
            loadDocuments()
        }
    }
    fun setOpenDocument(document: Document){
        interactors.setOpenDocument(document)
    }
}
