package com.example.paging3sample.room

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.*

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: BookDatabase? = null
        fun getDatabase(context: Context): BookDatabase {
            if (instance == null) { synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book_database"
                    ).build()
                }
            }
            return instance!!
        }
    }
}