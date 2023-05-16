package com.dinhpx.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dinhpx.test.adapter.ProgressTabAdapter
import com.dinhpx.test.databinding.FragmentStoryBinding
import com.dinhpx.test.utils.ResumeTimer

@SuppressLint("ClickableViewAccessibility")
class StoryFragment : Fragment() {

    companion object {
        private const val TIME_TAB = 2000L
    }

    private lateinit var binding: FragmentStoryBinding

    private val viewModel by activityViewModels<MainViewModel>()

    private var tabAdapter: ProgressTabAdapter? = null

    private var countDownTimer: ResumeTimer? = null


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
    }


    private fun initListener() {
        binding.vNext.setOnClickListener {
            scrollTab(viewModel.currentStory.currentImagePosition + 1)
        }

        binding.vPrev.setOnClickListener {
            scrollTab(viewModel.currentStory.currentImagePosition - 1)
        }
        /*binding.vPrev.setOnTouchListener(onTouchListener)
        binding.vNext.setOnTouchListener(onTouchListener)*/

    }

    private val onTouchListener = View.OnTouchListener { v, event ->
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                countDownTimer?.stop()
            }

            MotionEvent.ACTION_UP -> {
                countDownTimer?.start()
            }
        }
        false
    }


    private fun initView() {
        tabAdapter = ProgressTabAdapter(viewModel.currentStory.images.size)
        binding.rvTab.adapter = tabAdapter
        scrollTab(0)
    }



    private fun scrollTab(position: Int) {
        countDownTimer?.stop()
        if (viewModel.positionInRangeImages(position)) {
            viewModel.currentStory.currentImagePosition = position
            binding.imageView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    viewModel.currentStory.images[position]
                )
            )
            binding.tvPosition.text = viewModel.currentStory.name
            countDownTab(position)
        } else {
            if (viewModel.canBackPrevStory()) {
                viewModel.backStory()
            } else if (!viewModel.canGoToNextStory()) {
                scrollTab(0)
            } else if (viewModel.canGoToNextStory()) {
                viewModel.nextStory()
            }
        }
    }

    private fun countDownTab(position: Int) {
        tabAdapter?.unselectTab(position)
        countDownTimer = ResumeTimer(TIME_TAB, 50L, object : ResumeTimer.OnCountDownListener {
            override fun onTick(elapsed: Long) {
                tabAdapter?.setTabProgress(position, (elapsed.toFloat() / TIME_TAB * 100).toInt())
            }

            override fun onFinished() {
                tabAdapter?.selectTab(position)
                scrollTab(position + 1)
            }
        }).start()

    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isStopFragment) {
            countDownTimer?.start()
            viewModel.isStopFragment = false
        } else {
            scrollTab(viewModel.currentStory.currentImagePosition)
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
