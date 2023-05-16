package com.dinhpx.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dinhpx.test.data.StoryModel

class MainViewModel : ViewModel() {


    var isStopFragment: Boolean = false


    var currentStoryPosition = 0

    val stories = mutableListOf<StoryModel>()
    val currentStory: StoryModel
        get() = stories[currentStoryPosition]

    fun initListStory() {
        stories.clear()
        val colors = mutableListOf(
            R.color.black,
            R.color.purple_500,
            R.color.teal_200,
        )
        stories.add(StoryModel("1", colors.shuffled()))
        stories.add(StoryModel("2", colors.shuffled().takeLast(2)))

    }

    fun canBackPrevStory(): Boolean {
        return currentStory.currentImagePosition == 0 && currentStoryPosition > 0
    }

    fun canGoToNextStory(): Boolean {
        return currentStory.currentImagePosition == currentStory.images.lastIndex && currentStoryPosition < stories.lastIndex
    }

    fun positionInRangeImages(position: Int): Boolean {
        return position in currentStory.images.indices
    }

    var currentStoryPositionLiveData = MutableLiveData<Int>()
    fun nextStory() {
        currentStoryPositionLiveData.postValue(currentStoryPosition + 1)
    }

    fun backStory() {
        currentStoryPositionLiveData.postValue(currentStoryPosition - 1)
    }




}