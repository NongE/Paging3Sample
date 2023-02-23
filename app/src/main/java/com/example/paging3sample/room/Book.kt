package com.example.paging3sample.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_database")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "book_name")
    val bookName: String,
    @ColumnInfo(name = "book_publisher")
    val bookPublisher: String
)