package com.example.core.interactors

import com.example.core.data.repository.DocumentRepository
import com.example.core.domain.Document

class GetOpenDocument(
    private val documentRepository: DocumentRepository
){
    suspend operator fun invoke(document: Document) =
        documentRepository.getOpenDocument(document)
}