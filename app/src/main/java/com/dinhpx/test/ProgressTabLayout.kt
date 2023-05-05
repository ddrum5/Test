package com.dinhpx.test

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout

class ProgressTabLayout : TabLayout {

    companion object {
        private const val TOTAL_TIME = 2500L
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }


    abstract class TabSelectedListener {
        abstract fun onTabSelected(tab: Tab?)

        open fun onTabUnselected(tab: Tab?) {}

        open fun onTabReselected(tab: Tab?) {}
    }


    private var tabSelectedListener: TabSelectedListener? = null

    private var previousPosition = 0

    private var countDownTimer: CountDownTimer? = null


    private fun init() {

        addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabSelected(tab: Tab?) {
                tabSelectedListener?.onTabSelected(tab)
                val processBar = tab?.view?.findViewById<ProgressBar>(R.id.progressBar)

                processBar?.isVisible = true
                processBar?.progress = 0
                tab?.view?.findViewById<View>(R.id.viewUnselected)?.isVisible = false
                if ((tab?.position ?: 0) < previousPosition) {
                    getTabAt(previousPosition)?.view?.findViewById<ProgressBar>(R.id.progressBar)?.isVisible =
                        false
                    getTabAt(previousPosition)?.view?.findViewById<View>(R.id.viewUnselected)?.isVisible =
                        true
                }

                countDownTimer = object : CountDownTimer(TOTAL_TIME, 10) {
                    override fun onTick(millisUntilFinished: Long) {
                        val process = (1 - millisUntilFinished.toFloat() / TOTAL_TIME) * 100
                        processBar?.setProgress(process.toInt(), true)
                    }

                    override fun onFinish() {
                        processBar?.progress = 100
                        getTabAt((tab?.position ?: 0) + 1)?.select()
                    }
                }.start()


            }

            override fun onTabUnselected(tab: Tab?) {
                tabSelectedListener?.onTabUnselected(tab)
                previousPosition = tab?.position ?: 0
                countDownTimer?.cancel()
                tab?.view?.findViewById<ProgressBar>(R.id.progressBar)?.setProgress(100, true)

            }

            override fun onTabReselected(tab: Tab?) {
                tabSelectedListener?.onTabReselected(tab)
            }
        })


    }


    fun setTabSelectedListener(tabSelectedListener: TabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener
    }


}