package com.pdfreadercleanarchitecture.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object PdfReaderViewModelFactory: ViewModelProvider.Factory{

    lateinit var application: Application

    lateinit var dependencies: Interactors

    fun inject(application: Application , dependencies: Interactors){
        PdfReaderViewModelFactory.application = application
        PdfReaderViewModelFactory.dependencies = dependencies
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(PdfReaderViewModel::class.java.isAssignableFrom(modelClass)){
            return modelClass.getConstructor(Application::class.java , Interactors::class.java)
                .newInstance(
                    application ,
                    dependencies
                )
        }else{
            throw IllegalArgumentException("ViewModel must extend PdfReaderViewModel")
        }
    }

}