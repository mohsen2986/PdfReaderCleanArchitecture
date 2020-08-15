package com.example.core.data.dataSource

import com.example.core.domain.Bookmark
import com.example.core.domain.Document

interface BookmarkDataSource{
    suspend fun add(document: Document, bookmark:Bookmark)

    suspend fun read(document: Document):List<Bookmark>

    suspend fun remove(document:Document , bookmark:Bookmark)
}