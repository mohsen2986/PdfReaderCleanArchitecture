package com.example.core.data.repository

import com.example.core.data.dataSource.DocumentDataSource
import com.example.core.data.dataSource.OpenDocumentDataSource
import com.example.core.domain.Document

class DocumentRepository(
    private val documentDataSource: DocumentDataSource ,
    private val openDocumentDataSource: OpenDocumentDataSource
){
    suspend fun addDocument(document: Document) =
        documentDataSource.add(document)

    suspend fun getDocument() =
        documentDataSource.readAll()

    suspend fun removeDocument(document: Document) =
        documentDataSource.remove(document)

    fun setOpenDocument(document: Document) =
        openDocumentDataSource.setOpenDocument(document)

    fun getOpenDocument(document: Document) =
        openDocumentDataSource.getOpenDocument()
}