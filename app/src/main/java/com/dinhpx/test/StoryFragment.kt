package com.dinhpx.test

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar

@SuppressLint("ClickableViewAccessibility")
class StoryFragment : Fragment() {

    companion object {
        private const val TIME_TAB = 2000L
    }

    private lateinit var binding: FragmentStoryBinding

    private val viewModel by activityViewModels<MainViewModel>()

    private var tabAdapter: ProgressTabAdapter? = null

    private var countDownTimer: ResumeTimer? = null

    private lateinit var storyListener: StoryListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        storyListener = requireContext() as StoryListener
    }

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
            scrollTabAt(viewModel.currentImagePosition + 1)
        }

        binding.vPrev.setOnClickListener {
            scrollTabAt(viewModel.currentImagePosition - 1)
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
        scrollTabAt(0)
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isRestartStory) {
            scrollTabAt(0)
        } else {
            countDownTimer?.start()
        }
        viewModel.isRestartStory = false
    }


    override fun onPause() {
        super.onPause()
        countDownTimer?.stop()
    }


    private fun scrollTabAt(position: Int) {
        try {
            viewModel.isRestartStory = false
            viewModel.currentImagePosition = position
            countDownTimer?.stop()
            if (viewModel.canBackPrevStory()) {
                storyListener.onPrevStory()
            } else if (viewModel.canGoToNextStory()) {
                storyListener.onNextStory()
            } else if (viewModel.isOutFirstImageOfFirstStory()) {
                scrollTabAt(0)
            } else if (viewModel.isOutLastImageOfLastStory()) {
                viewModel.currentImagePosition = viewModel.currentStory.images.lastIndex
                return
            } else {
                if (position == 0) {
                    tabAdapter?.unSelectAllTab()
                } else if (position < viewModel.previousImagePosition) {
                    tabAdapter?.unselectTab(viewModel.previousImagePosition)
                } else if (position != 0) {
                    tabAdapter?.progressTab(viewModel.previousImagePosition, 100, isSmooth = false)
                }
                binding.imageView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        viewModel.currentStory.images[position]
                    )
                )
                viewModel.previousImagePosition = position
                countDownTab(position)
            }
        } catch (e: Exception) {
            Snackbar.make(binding.root, "Có lỗi xảy ra", Snackbar.LENGTH_SHORT).show()
            Log.e("DINHTEST error", e.message.toString())
        }

    }

    private fun countDownTab(position: Int) {
        tabAdapter?.progressTab(position, 0, isSmooth = false)

        countDownTimer = ResumeTimer(TIME_TAB, 50L, object : ResumeTimer.OnCountDownListener {
            override fun onTick(elapsed: Long) {
                val process = (elapsed.toFloat() / TIME_TAB * 100).toInt()
                tabAdapter?.progressTab(position, process)
            }

            override fun onFinished() {
                tabAdapter?.progressTab(position, 100, isSmooth = false)
                scrollTabAt(position + 1)
            }
        }).start()

    }


}
