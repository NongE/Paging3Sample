package com.example.paging3sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging3sample.room.Book
import com.example.paging3sample.room.BookDao
import kotlinx.coroutines.flow.Flow

class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    val data: Flow<PagingData<Book>> = Pager(
        config = PagingConfig(10)
    ) { BookPagingSource(bookDao) }
        .flow
        .cachedIn(viewModelScope)

    fun insertData() {
        for (i in 0..10000) {
            bookDao.insertBook(Book(bookName = "BookName $i", bookPublisher = "BookPublisher $i}"))
        }
    }
}

class BookViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(bookDao = bookDao) as T
    }
}