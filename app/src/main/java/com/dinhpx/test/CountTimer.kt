package com.dinhpx.test

import kotlinx.coroutines.*

class CountTimer(
    val callBack: ((id: Int, countTime: String) -> Unit)
) {


    private var timeStart = System.currentTimeMillis()
    private var isRunning = false
    private var id = 0

    private fun count() {
        CoroutineScope(Dispatchers.Default).launch {
            while (isRunning) {
                val time = (System.currentTimeMillis() - timeStart) / 1000F
                val timeString = String.format("%.1f", time)
                callBack(id, timeString)
                delay(10)
            }
        }
    }



    fun start() {
        timeStart = System.currentTimeMillis()
        isRunning = true
        count()
    }

    fun stop() {
        isRunning = false
    }


}