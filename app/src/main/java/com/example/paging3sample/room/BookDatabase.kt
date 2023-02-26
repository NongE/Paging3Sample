package com.example.paging3sample.room

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: BookDatabase? = null
        fun getDatabase(context: Context): BookDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book_database"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            generateDatabase(context)
                        }
                    })
                        .build()
                }
            }
            return instance!!
        }

        fun generateDatabase(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).bookDao()
                for (i in 100 downTo 1) {
                    Log.i("Paging3Log", "DB Insert!")
                    dao.insertBook(
                        Book(bookName = "BookName $i", bookPublisher = "BookPublisher $i")
                    )
                }
            }
        }
    }
}