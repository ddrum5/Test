package com.dinhpx.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "DINHPXTEST"
    }
     val listData = mutableListOf<String>()

    var id = 0

    var countLiveData = MutableLiveData<Pair<Int, String>>()

    fun getCount() {
        if (id > listData.lastIndex) id = 0
        CoroutineScope(Dispatchers.IO).launch {
            val index = id
            val one = async { getOne() }
            launch {
                val data = one.await().toString()
                countLiveData.postValue(Pair(index, data))
            }

            launch {
                var count = 0.0
                val delay = 10L
                while (!one.isCompleted) {
                    delay(delay)
                    count += delay.toFloat()
                    countLiveData.postValue(Pair(index, (count/1000).toString()))
                }
            }
            id++
        }
    }

    suspend fun getOne(): Int {
        delay(4000)
        return 10
    }

}