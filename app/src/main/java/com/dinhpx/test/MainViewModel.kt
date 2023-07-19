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
import kotlin.system.measureTimeMillis

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "DINHPXTEST"
        const val MAX_SIZE = 10000
    }

    val listData = mutableListOf<CouData>()

    var currentPosition = 0
    val timeDelay = 1L

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
            val a = measureTimeMillis {
                getOne()
                isRun = false
            }

            Log.d(TAG, "getCount: a = $a")

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