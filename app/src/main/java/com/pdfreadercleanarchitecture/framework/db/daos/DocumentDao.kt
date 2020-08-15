package com.pdfreadercleanarchitecture.framework.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.pdfreadercleanarchitecture.framework.db.entities.DocumentEntity

@Dao
interface DocumentDao{

    @Insert(onConflict = REPLACE)
    suspend fun addDocument(document: DocumentEntity)

    @Query("SELECT * FROM document")
    suspend fun getDocuments(): List<DocumentEntity>

    @Delete
    suspend fun removeDocument(document: DocumentEntity)
}