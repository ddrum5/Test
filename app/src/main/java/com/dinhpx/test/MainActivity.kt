package com.dinhpx.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dinhpx.test.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DINHPXTEST"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private val dialog by lazy { LoadingDialog(this) }

    private val adapter = TextAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvList.adapter = adapter
        (binding.rvList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false



        binding.button.setOnClickListener {
            repeat(50) {
                lifecycleScope.launch {
                    viewModel.getCount().collect {
                        adapter.updateData(it)
                    }
                }
            }

        }


    }

}