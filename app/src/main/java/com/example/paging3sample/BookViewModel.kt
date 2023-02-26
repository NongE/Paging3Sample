package com.example.paging3sample

import android.content.Context
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

class BookViewModel(private val context: Context) : ViewModel() {
    private val repository = BookRepository(context)
    val bookData = repository.getBookData().cachedIn(viewModelScope)
}

class BookViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(context = context) as T
    }
}