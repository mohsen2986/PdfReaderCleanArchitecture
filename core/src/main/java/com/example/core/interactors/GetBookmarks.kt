package com.example.core.interactors

import com.example.core.data.repository.BookmarkRepository
import com.example.core.domain.Document

class GetBookmarks(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke(document: Document) =
        bookmarkRepository.getBookmark(document)
}