package com.dinhpx.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dinhpx.test.adapter.DepthPageTransformer
import com.dinhpx.test.adapter.ViewPagerAdapter
import com.dinhpx.test.databinding.ActivityMainBinding
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    companion object {
        private const val MAX_SCALE = 1f
        private const val SCALE_PERCENT = 0.8f
        private const val MIN_SCALE = SCALE_PERCENT * MAX_SCALE
        private const val MAX_ALPHA = 1.0f
        private const val MIN_ALPHA = 0.3f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startActivity(Intent(this, Webview::class.java))
        finish()
        initView()
        initObserve()
    }

    private fun initView() {
        viewModel.initListStory()

        val viewPagerAdapter = ViewPagerAdapter(this, viewModel.stories.size)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.setPageTransformer(DepthPageTransformer())


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.currentStoryPosition = position
            }
        })
    }

    private fun initObserve() {
        viewModel.currentStoryPositionLiveData.observe(this) {
            binding.viewPager.currentItem = it
        }
    }




}