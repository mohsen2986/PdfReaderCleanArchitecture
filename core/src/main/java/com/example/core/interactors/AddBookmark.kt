package com.example.core.interactors

import com.example.core.data.repository.BookmarkRepository
import com.example.core.domain.Bookmark
import com.example.core.domain.Document

class AddBookmark(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke(document: Document, bookmark: Bookmark) =
        bookmarkRepository.addBookmark(document  , bookmark)
}