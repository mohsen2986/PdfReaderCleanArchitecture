package com.pdfreadercleanarchitecture.framework.db.dataSourceImpls

import com.example.core.data.dataSource.OpenDocumentDataSource
import com.example.core.domain.Document

class InMemoryOpenDocumentDataSource(
): OpenDocumentDataSource{
    private var openDocument: Document = Document.EMPTY

    override fun setOpenDocument(document: Document) {
        openDocument = document
    }

    override fun getOpenDocument(): Document =
        openDocument

}