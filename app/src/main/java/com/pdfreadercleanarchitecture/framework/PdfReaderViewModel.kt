package com.pdfreadercleanarchitecture.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class PdfReaderViewModel(
    application: Application,
    protected val interactors: Interactors
):AndroidViewModel(application){
    protected val application: PdfReaderApplication = getApplication()
}