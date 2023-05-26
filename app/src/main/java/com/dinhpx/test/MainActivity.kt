package com.dinhpx.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.dinhpx.test.databinding.ActivityMainBinding
import com.dinhpx.test.utils.toPx
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    companion object {
        private const val MAX_ALPHA = 1.0f
        private const val MIN_ALPHA = 0.3f
        private const val MAX_SCALE = 1f
        private const val SCALE_PERCENT = 0.8f
        private const val MIN_SCALE = SCALE_PERCENT * MAX_SCALE
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initObserve()
    }

    private fun initView() {

        val viewPagerAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = viewModel.stories.size
            override fun createFragment(position: Int) = StoryFragment(position)
        }

        with(binding.viewPager) {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.currentStoryPosition = position
                }
            })
            clipToPadding = false
            clipChildren = false
            // retain 1 page on each size
            offscreenPageLimit = 1
            this.adapter = adapter
            val screenHeight = resources.displayMetrics.heightPixels
            setPageTransformer { view, position ->
                // position  -1: left, 0: center, 1: right
                val absPosition = abs(position)
                // alpha from MIN_ALPHA to MAX_ALPHA
                view.alpha = MAX_ALPHA - (MAX_ALPHA - MIN_ALPHA) * absPosition
                // scale from MIN_SCALE to MAX_SCALE
                val scale = MAX_SCALE - (MAX_SCALE - MIN_SCALE) * absPosition
                view.scaleY = scale
                view.scaleX = scale
            }
        }

    }


    private fun initObserve() {
        viewModel.currentStoryPositionLiveData.observe(this) {
            binding.viewPager.currentItem = it
        }
    }


}