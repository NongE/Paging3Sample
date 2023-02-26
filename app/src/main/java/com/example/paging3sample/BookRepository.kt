package com.example.paging3sample

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paging3sample.room.Book
import com.example.paging3sample.room.BookDao
import com.example.paging3sample.room.BookDatabase
import kotlinx.coroutines.flow.Flow

class BookRepository(context: Context) {
    private val dao = BookDatabase.getDatabase(context).bookDao()

    fun getBookData(): Flow<PagingData<Book>> {
        return Pager(config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 10
        )) {
            BookPagingSource(dao)
        }.flow
    }
}