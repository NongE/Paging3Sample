package com.example.paging3sample.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {

    @Query("SELECT * FROM book_database ORDER BY id DESC LIMIT :loadSize OFFSET :index * :loadSize")
    suspend fun getAllBook(index: Int, loadSize: Int): List<Book>

    @Insert
    fun insertBook(data: Book)
}