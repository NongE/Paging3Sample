package com.example.paging3sample

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3sample.room.Book
import com.example.paging3sample.room.BookDao
import kotlinx.coroutines.delay

class BookPagingSource(
    private val bookDao: BookDao
) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try{
            val next = params.key ?: 0
            val response = bookDao.getAllBook(next, params.loadSize)

            Log.i("Paging3Log", "Load new data (Limit: ${params.loadSize}, Offset: ${next * params.loadSize})")

            LoadResult.Page(
                data = response,
                prevKey = if (next == 0) null else next - 1,
                nextKey = if (response.isEmpty()) null else next + 1
            )
        }catch (e: Exception){
            Log.i("Paging3Log", "Throw exception")
            LoadResult.Error(e)
        }
    }
}