package com.dinhpx.test.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.TimeUnit

class ResumeTimer(
    private val duration: Long, // in milliseconds
    private val tick: Long = TimeUnit.SECONDS.toMillis(1), // in milliseconds
    private val onCountDownListener: OnCountDownListener
) {
    interface OnCountDownListener {
        fun onTick(elapsed: Long) // in milliseconds
        fun onFinished()
    }

    private var handler = Handler(Looper.getMainLooper())
    private var elapsed = 0L

    private lateinit var runnable: Runnable

    init {
        runnable = Runnable {
            elapsed += tick
            onCountDownListener.onTick(elapsed)
            if (elapsed < duration) {
                handler.postDelayed(runnable, tick)
            } else {
                stop()
                onCountDownListener.onFinished()
            }
        }
    }

    fun start(restart: Boolean = false): ResumeTimer {
        if (restart) {
            handler.removeCallbacks(runnable)
            elapsed = 0L
        }

        handler.postDelayed(runnable, tick)
        return this
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }
}