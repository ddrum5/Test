package com.dinhpx.test

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.adapter.LoadMoreAdapter
import com.dinhpx.test.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter by lazy { LoadMoreAdapter() }

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.recyclerView.adapter = adapter
        layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        adapter.showLoading()
        viewModel.getData()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItem = layoutManager.itemCount - 1
                Log.d("DINHPXTEST addOnScrollListener isLoading", adapter.isLoading().toString())
                Log.d("DINHPXTEST addOnScrollListener lastItemPosition", lastItemPosition.toString())
                Log.d("DINHPXTEST addOnScrollListener itemCount", totalItem.toString())
                Log.d("DINHPXTEST addOnScrollListener", "==================")
                Log.d("DINHPXTEST", "")


                if (!adapter.isLoading() && lastItemPosition == totalItem && adapter.itemCount < 40) {
                    adapter.showLoading()
                    viewModel.getData()
                    Log.d("DINHPXTEST getData", "")

                }
            }
        })

        viewModel.listItemDataLiveData.observe(this) {
            adapter.addData(it)
        }

    }


}