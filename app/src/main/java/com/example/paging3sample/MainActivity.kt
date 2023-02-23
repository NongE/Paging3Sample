package com.example.paging3sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paging3sample.databinding.ActivityMainBinding
import com.example.paging3sample.room.BookDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter

    private val viewModel: BookViewModel by lazy {
        ViewModelProvider(
            owner = this,
            factory = BookViewModelFactory(BookDatabase.getDatabase(applicationContext).bookDao())
        )[BookViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BookAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.data.collectLatest { adapter.submitData(it) }
        }

        binding.add.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insertData()
            }
        }
    }
}