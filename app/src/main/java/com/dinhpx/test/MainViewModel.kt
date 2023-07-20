package com.dinhpx.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "DINHPXTEST"
        const val MAX_SIZE = 10000
    }

    val listData = mutableListOf<CouData>()

    var currentPosition = 0
    val timeDelay = 50L

    fun getCount() = flow {
        if (currentPosition > listData.lastIndex) currentPosition = 0
        val index = currentPosition
        currentPosition++

        if (listData.size < MAX_SIZE) {
            listData.add(CouData(position = currentPosition))
        }
        var isRun = true
        val timeStart = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO) {
            getOne()
            isRun = false
        }
        while (isRun) {
            val time = System.currentTimeMillis() - timeStart
            emit(
                CouData(
                    index,
                    String.format("%.2f", (time / 1000F)),
                    Thread.currentThread().name
                )
            )
        }

    }.onEach { delay(timeDelay) }


    suspend fun getOne(): Int {
        delay((10L..20).random() * 1000)
        return 5
    }

    class CouData(
        val position: Int,
        val time: String = "",
        val threadName: String = ""
    )

}