package com.dinhpx.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dinhpx.test.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initObserve()
    }

    private fun initView() {
        viewModel.initListStory()

        val viewPagerAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = viewModel.stories.size
            override fun createFragment(position: Int) = StoryFragment()
        }

        binding.viewPager.adapter = viewPagerAdapter


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