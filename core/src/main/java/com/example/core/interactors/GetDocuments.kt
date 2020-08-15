package com.example.core.interactors

import com.example.core.data.repository.DocumentRepository

class GetDocuments(
    private val documentRepository: DocumentRepository
){
    suspend operator fun invoke() =
        documentRepository.getDocument()
}