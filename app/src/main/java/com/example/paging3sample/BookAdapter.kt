package com.example.paging3sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.databinding.ActivityMainBinding
import com.example.paging3sample.databinding.ItemBookBinding
import com.example.paging3sample.room.Book


class BookAdapter : PagingDataAdapter<Book, BookAdapter.BookViewHolder>(diffCallback) {

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.bookName.text = book.bookName
            binding.bookPublish.text = book.bookPublisher
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return BookViewHolder(
            ItemBookBinding.inflate(layoutInflater, parent, false)
        )
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}