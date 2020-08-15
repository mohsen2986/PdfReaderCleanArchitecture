package com.example.core.data.dataSource

import com.example.core.domain.Document


interface DocumentDataSource{
    suspend fun add(document: Document)

    suspend fun readAll():List<Document>

    suspend fun remove(document: Document)
}