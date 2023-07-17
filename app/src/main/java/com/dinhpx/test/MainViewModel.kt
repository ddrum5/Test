package com.dinhpx.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "DINHPXTEST"
    }

    val loadingLiveData = MutableLiveData<Boolean>()


    suspend fun getOne(): Int {
        delay(4000)
        return 10
    }

    suspend fun getTwo(): Int {
        delay(3000)
        return 20
    }
}