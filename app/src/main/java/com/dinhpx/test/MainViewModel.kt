package com.dinhpx.test

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "DINHPXTEST"
        const val MAX_SIZE = 10
    }

    val listData = mutableListOf<CouData>()

    var currentPosition = 0
    val timeDelay = 10L

    fun getCount() = flow {
        if (currentPosition > listData.lastIndex) currentPosition = 0
        val index = currentPosition
        currentPosition++

        if (listData.size < MAX_SIZE) {
            listData.add(CouData(position = currentPosition))
        }

        var isRun = true
        var count = 0.0
        viewModelScope.launch(Dispatchers.IO) {
            getOne()
            isRun = false
        }
        while (isRun) {
            count += timeDelay.toFloat()
            val stringCount =  (count / 1000).toString()
            emit(CouData(index, String.format("%.1f", stringCount), Thread.currentThread().name))
        }

    }.onEach { delay(timeDelay) }


    suspend fun getOne(): Int {
        delay((4L..10).random() * 1000)
        return 5
    }

    class CouData(
        val position: Int,
        val time: String = "",
        val threadName: String = ""
    )

}