package com.pdfreadercleanarchitecture.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pdfreadercleanarchitecture.framework.db.daos.BookmarkDao
import com.pdfreadercleanarchitecture.framework.db.daos.DocumentDao
import com.pdfreadercleanarchitecture.framework.db.entities.BookmarkEntity
import com.pdfreadercleanarchitecture.framework.db.entities.DocumentEntity

@Database(
    entities = [DocumentEntity::class , BookmarkEntity::class] ,
    version = 1
)
abstract class PdfReaderDataBase():RoomDatabase(){
    abstract fun BookmarkDao(): BookmarkDao
    abstract fun DocumentDao(): DocumentDao

    companion object {
        @Volatile
        private var instance: PdfReaderDataBase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                PdfReaderDataBase::class.java,
                "pdf_reader_db"
            )
                .build()
    }
}