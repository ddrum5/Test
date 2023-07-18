package com.dinhpx.test

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
        const val MAX_SIZE = 15
    }

    val listData = mutableListOf<CouData>()

    var currentPosition = 0
    val timeDelay = 200L

    fun getCount() = flow<CouData> {
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
            emit(CouData(index, (count / 1000).toString(), Thread.currentThread().name))
        }

    }.onEach { delay(timeDelay) }


    suspend fun getOne(): Int {
        delay(4000)
        return 5
    }

    class CouData(
        val position: Int,
        val time: String = "",
        val threadName: String = ""
    )

}