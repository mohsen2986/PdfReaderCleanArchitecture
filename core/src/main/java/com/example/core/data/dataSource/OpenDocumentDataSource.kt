package com.example.core.data.dataSource

import com.example.core.domain.Document


interface OpenDocumentDataSource{
    fun setOpenDocument(document: Document)

    fun getOpenDocument(): Document
}