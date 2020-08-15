package com.example.core.data.repository

import com.example.core.data.dataSource.BookmarkDataSource
import com.example.core.domain.Bookmark
import com.example.core.domain.Document

class BookmarkRepository(
    private val bookmarkDataSource:BookmarkDataSource
    ){
    suspend fun addBookmark(document: Document , bookmark: Bookmark) =
        bookmarkDataSource.add(document , bookmark)

    suspend fun getBookmark(document: Document) =
        bookmarkDataSource.read(document)

    suspend fun removeBookmark(document: Document , bookmark: Bookmark) =
        bookmarkDataSource.remove(document , bookmark)
}