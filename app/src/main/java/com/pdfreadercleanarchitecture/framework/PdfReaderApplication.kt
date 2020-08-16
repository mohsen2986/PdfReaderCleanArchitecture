package com.pdfreadercleanarchitecture.framework

import android.app.Application
import com.example.core.data.repository.BookmarkRepository
import com.example.core.data.repository.DocumentRepository
import com.example.core.interactors.*
import com.pdfreadercleanarchitecture.framework.db.dataSourceImpls.InMemoryOpenDocumentDataSource
import com.pdfreadercleanarchitecture.framework.db.dataSourceImpls.RoomBookmarkDataSourceImpl
import com.pdfreadercleanarchitecture.framework.db.dataSourceImpls.RoomDocumentDataSourceImpl

class PdfReaderApplication(
): Application(){

    override fun onCreate() {
        super.onCreate()

        val bookmarkRepository = BookmarkRepository(RoomBookmarkDataSourceImpl(this))
        val documentRepository = DocumentRepository(
            RoomDocumentDataSourceImpl(this) ,
            InMemoryOpenDocumentDataSource()
        )
        PdfReaderViewModelFactory.inject(
            this ,
            Interactors(
                AddBookmark(bookmarkRepository) ,
                GetBookmarks(bookmarkRepository) ,
                RemoveBookmark(bookmarkRepository) ,
                AddDocument(documentRepository) ,
                GetDocuments(documentRepository) ,
                RemoveDocument(documentRepository) ,
                GetOpenDocument(documentRepository) ,
                SetOpenDocument(documentRepository)
            )
        )

    }
}