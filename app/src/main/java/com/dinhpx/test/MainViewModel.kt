package com.dinhpx.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhpx.test.data.ItemData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val listItemDataLiveData = MutableLiveData<List<ItemData>>()
    fun getData() {
        viewModelScope.launch {
            val listData = mutableListOf<ItemData>()
            repeat(2) {
                listData.add(ItemData())
            }

            delay(1500)
            listItemDataLiveData.postValue(listData)
        }

    }
}