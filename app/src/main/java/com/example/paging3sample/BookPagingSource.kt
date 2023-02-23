package com.example.paging3sample

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3sample.room.Book
import com.example.paging3sample.room.BookDao

class BookPagingSource(private val bookDao: BookDao) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val position = params.key ?: 0
        val bookData = bookDao.getAllBook(position, params.loadSize)

        Log.i("Paging3 Log", "load ${params.loadSize} count")

        return LoadResult.Page(
            data = bookData,
            prevKey = if (position == 0) null else position - 1,
            nextKey = if (bookData.isEmpty()) null else position + 1
        )
    }
}