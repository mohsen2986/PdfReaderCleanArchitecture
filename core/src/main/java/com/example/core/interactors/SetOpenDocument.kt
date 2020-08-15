package com.example.core.interactors

import com.example.core.data.repository.DocumentRepository
import com.example.core.domain.Document

class SetOpenDocument(
    private val documentRepository: DocumentRepository
){
    suspend operator fun invoke(document: Document) =
        documentRepository.setOpenDocument(document)
}