package com.dinhpx.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dinhpx.test.adapter.ProgressTabAdapter
import com.dinhpx.test.databinding.FragmentStoryBinding
import com.dinhpx.test.utils.ResumeTimer

@SuppressLint("ClickableViewAccessibility")
class StoryFragment(private val position: Int) : Fragment() {

    companion object {
        private const val TIME_TAB = 2000L
    }

    private lateinit var binding: FragmentStoryBinding
    private val viewModel by activityViewModels<MainViewModel>()

    private var countDownTimer: ResumeTimer? = null
    private lateinit var tabAdapter: ProgressTabAdapter

    private val toast by lazy { Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT) }


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


    private fun initView() {
        viewModel.currentStoryPosition = position
        binding.tvPosition.text = "Story ${position + 1}"
        tabAdapter = ProgressTabAdapter(viewModel.currentStory.images.size)
        binding.rvTab.adapter = tabAdapter

        countDownTimer = ResumeTimer(TIME_TAB, 100L, object : ResumeTimer.OnCountDownListener {
            override fun onTick(elapsed: Long) {
                tabAdapter.setTabProgress(process = (elapsed.toFloat() / TIME_TAB * 100).toInt())
            }

            override fun onFinished() {
                tabAdapter.selectTab()
                nextImage()
            }
        })

        updateImage(0)
    }


    private fun initListener() {
        binding.vPrev.setOnTouchListener { _, event ->
            handleAction(event) {
                backImage()
            }
        }

        binding.vNext.setOnTouchListener { _, event ->
            handleAction(event) {
                nextImage()
            }
        }
    }

    private var timeHoldDown = 0L
    private fun handleAction(event: MotionEvent?, action: () -> Unit): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                countDownTimer?.stop()
                timeHoldDown = SystemClock.elapsedRealtime()
            }
            MotionEvent.ACTION_UP -> {
                if (SystemClock.elapsedRealtime() - timeHoldDown < 500) {
                    countDownTimer?.stop()
                    action()
                } else {
                    countDownTimer?.start()
                }
            }
        }
        return true
    }

    private fun nextImage(position: Int = tabAdapter.getCurrentTabPosition()) {
        if (viewModel.isLastImageOfLastStory(position)) {
            toast.setText("This is last story")
            toast.show()
            tabAdapter.selectTab(position)
        } else if (viewModel.isLastImageOfStory(position)) {
            viewModel.nextStory()
        } else {
            updateImage(position + 1)
        }
    }

    private fun backImage(position: Int = tabAdapter.getCurrentTabPosition()) {
        if (viewModel.isFirstImageOfFirstStory(position)) {
            updateImage(0)
        } else if (viewModel.isFirstImageOfStory(position)) {
            viewModel.backStory()
        } else {
            updateImage(position - 1)
        }
    }


    private fun updateImage(position: Int) {
        toast.cancel()
        binding.imageView.setImageResource(viewModel.currentStory.images[position])
        tabAdapter.setCurrentTab(position)
        countDownTimer?.start(restart = true)
    }


    override fun onResume() {
        super.onResume()
        viewModel.currentStoryPosition = position
        if (viewModel.isStopFragment) {
            viewModel.isStopFragment = false
            countDownTimer?.start()
        } else if (!countDownTimer!!.isRunning()) {
            updateImage(tabAdapter.getCurrentTabPosition())
        }
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.stop()
        toast.cancel()
    }

    override fun onStop() {
        super.onStop()
        viewModel.isStopFragment = true
    }

}
