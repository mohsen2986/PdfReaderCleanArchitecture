package com.pdfreadercleanarchitecture.framework.db.dataSourceImpls

import android.content.Context
import com.example.core.data.dataSource.DocumentDataSource
import com.example.core.domain.Document
import com.pdfreadercleanarchitecture.framework.utils.FileUtil
import com.pdfreadercleanarchitecture.framework.db.PdfReaderDataBase
import com.pdfreadercleanarchitecture.framework.db.entities.DocumentEntity

class RoomDocumentDataSourceImpl(
    val context: Context
):DocumentDataSource{
    private val documentDao = PdfReaderDataBase.invoke(context).DocumentDao()

    override suspend fun add(document: Document) {
        val details = FileUtil.getDocumentDetails(context , document.url)

        return documentDao.addDocument(
            DocumentEntity(document.url , details.name , details.size , details.thumbnail )
        )
    }

    override suspend fun readAll(): List<Document>  = documentDao.getDocuments().map{
        Document(
            it.uri ,
            it.title ,
            it.size ,
            it.thumbnailUri
        )
    }

    override suspend fun remove(document: Document) =
        documentDao.removeDocument(DocumentEntity(
            document.url ,
            document.name ,
            document.size  ,
            document.thumbnail
        ))


}