package com.pdfreadercleanarchitecture.framework.db.dataSourceImpls

import android.content.Context
import com.example.core.data.dataSource.BookmarkDataSource
import com.example.core.domain.Bookmark
import com.example.core.domain.Document
import com.pdfreadercleanarchitecture.framework.db.PdfReaderDataBase
import com.pdfreadercleanarchitecture.framework.db.entities.BookmarkEntity

class RoomBookmarkDataSourceImpl(
    context: Context
): BookmarkDataSource{
    private val bookmarkDao = PdfReaderDataBase.invoke(context).BookmarkDao()

    override suspend fun add(document: Document, bookmark: Bookmark) {
        bookmarkDao.addBookmark(BookmarkEntity(
            documentUri = document.url ,
            page = bookmark.page
        ))
    }

    override suspend fun read(document: Document): List<Bookmark> =
        bookmarkDao.getBookmarks(documentUri = document.url).map { Bookmark(it.id , it.page) }

    override suspend fun remove(document: Document, bookmark: Bookmark) {
        bookmarkDao.removeBookmark(
            BookmarkEntity(id = bookmark.id , documentUri = document.url , page = bookmark.page)
        )
    }

}