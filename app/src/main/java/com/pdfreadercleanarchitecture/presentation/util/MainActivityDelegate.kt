package com.pdfreadercleanarchitecture.presentation.util

import com.example.core.domain.Document


interface MainActivityDelegate {

    fun openDocument(document: Document)
}