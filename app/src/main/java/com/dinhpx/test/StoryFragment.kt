package com.dinhpx.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dinhpx.test.adapter.ProgressTabAdapter
import com.dinhpx.test.databinding.FragmentStoryBinding
import com.dinhpx.test.model.Story
import com.dinhpx.test.utils.ResumeTimer

@SuppressLint("ClickableViewAccessibility")
class StoryFragment(private val position: Int) : Fragment() {

    companion object {
        private const val TIME_TAB = 2000L
    }

    private lateinit var binding: FragmentStoryBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private var countDownTimer: ResumeTimer? = null

    private lateinit var story: Story
    private lateinit var tabAdapter: ProgressTabAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initObserve()
    }


    private fun initView() {
        story = viewModel.stories[position]
        tabAdapter = ProgressTabAdapter(story.images.size)
        binding.rvTab.adapter = tabAdapter
        binding.tvPosition.text = story.name
        viewModel.setImagePosition(0)
    }


    private fun initListener() {
        binding.vPrev.setOnTouchListener { v, event ->
            action(event, isNext = false)
        }

        binding.vNext.setOnTouchListener { v, event ->
            action(event, isNext = true)
        }
    }


    private var timeHoldDown = 0L
    private fun action(event: MotionEvent?, isNext: Boolean): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                countDownTimer?.stop()
                timeHoldDown = SystemClock.elapsedRealtime()
            }
            MotionEvent.ACTION_UP -> {
                if (SystemClock.elapsedRealtime() - timeHoldDown < 300) {
                    if (isNext)
                        viewModel.nextImage(tabAdapter.getCurrentTabPosition())
                    else
                        viewModel.backImage(tabAdapter.getCurrentTabPosition())
                } else {
                    countDownTimer?.start()
                }
            }
        }
        return true
    }

    private fun initObserve() {
        viewModel.currentImagePositionLiveData.observe(viewLifecycleOwner) {
            binding.imageView.setImageResource(story.images[it])
            countDownTab(it)
        }
    }


    private fun countDownTab(position: Int) {
        tabAdapter.setTabInProgress(position)
        countDownTimer?.stop()
        countDownTimer = ResumeTimer(TIME_TAB, 50L, object : ResumeTimer.OnCountDownListener {
            override fun onTick(elapsed: Long) {
                tabAdapter.setTabProgress(position, (elapsed.toFloat() / TIME_TAB * 100).toInt())
            }

            override fun onFinished() {
                tabAdapter.selectTab(position)
                viewModel.nextImage(position)
            }
        }).start()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isStopFragment) {
            countDownTimer?.start()
            viewModel.isStopFragment = false
        } else {
            countDownTab(tabAdapter.getCurrentTabPosition())
        }

    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.stop()
    }

    override fun onStop() {
        super.onStop()
        viewModel.isStopFragment = true
    }

}
