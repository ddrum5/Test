package com.dinhpx.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dinhpx.test.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DINHPXTEST"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private val dialog by lazy { LoadingDialog(this) }

    private val adapter = TextAdapter()
    private val listData = mutableListOf(
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.list.adapter = adapter
        adapter.setData(listData)


        binding.button.setOnClickListener {

        }




    }

}