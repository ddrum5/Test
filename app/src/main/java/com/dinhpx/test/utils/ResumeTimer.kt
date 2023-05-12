package com.dinhpx.test.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.TimeUnit

class ResumeTimer(
    private var duration: Long, // in milliseconds
    private var tick: Long = TimeUnit.SECONDS.toMillis(1), // in milliseconds
    private val listener: OnCountDownListener?
) {

    interface OnCountDownListener {
        fun onTick(elapsed: Long) // in milliseconds
        fun onFinished()
    }

    private var handler = Handler(Looper.getMainLooper())
    var elapsed = 0L

    private lateinit var runnable: Runnable

    init {
        runnable = Runnable {
            elapsed += tick
            listener?.onTick(elapsed)
            if (elapsed < duration) {
                handler.postDelayed(runnable, tick)
            } else {
                stop()
                listener?.onFinished()
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