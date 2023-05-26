package com.dinhpx.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dinhpx.test.model.StoryModel

class MainViewModel : ViewModel() {
    /*var isStopFragment: Boolean = false


    var currentStoryPosition = 0
    var currentStoryPositionLiveData = MutableLiveData<Int>()
    val stories = mutableListOf<StoryModel>()
    val currentStory: StoryModel
        get() = stories[currentStoryPosition]


    init {
        stories.clear()
        val colors = mutableListOf(
            R.color.black,
            R.color.purple_500,
            R.color.teal_200,
        )
        stories.add(StoryModel(medias = colors.shuffled()))
        stories.add(StoryModel(medias = colors.shuffled().takeLast(2)))
        stories.add(StoryModel(medias = colors.shuffled().takeLast(3)))
    }


    fun nextStory() {
        currentStoryPositionLiveData.value = currentStoryPosition + 1
    }

    fun backStory() {
        currentStoryPositionLiveData.value = currentStoryPosition - 1
    }

    fun isFirstImageOfStory(position: Int): Boolean {
        return position == 0
    }

    fun isFirstImageOfFirstStory(position: Int): Boolean {
        return position == 0 && currentStoryPosition == 0
    }

    fun isLastImageOfStory(position: Int): Boolean {
        return position == currentStory.medias.lastIndex
    }

    fun isLastImageOfLastStory(position: Int): Boolean {
        return position == currentStory.medias.lastIndex && currentStoryPosition == stories.lastIndex
    }*/


}