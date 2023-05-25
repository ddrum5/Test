package com.dinhpx.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dinhpx.test.model.Story

class MainViewModel : ViewModel() {
    var isStopFragment: Boolean = false


    var currentStoryPosition = 0
    var currentStoryPositionLiveData = MutableLiveData<Int>()
    val stories = mutableListOf<Story>()
    private val currentStory: Story
        get() = stories[currentStoryPosition]


    val currentImagePositionLiveData = MutableLiveData<Int>()


    init {
        stories.clear()
        val colors = mutableListOf(
            R.color.black,
            R.color.purple_500,
            R.color.teal_200,
        )
        stories.add(Story("story 1", colors.shuffled()))
        stories.add(Story("story 2", colors.shuffled().takeLast(2)))
    }


    fun nextImage(position: Int) {
        if (isLastImageOfLastStory(position)) {
            setImagePosition(position)
        } else if (isLastImageOfStory(position)) {
            nextStory()
        } else {
            setImagePosition(position + 1)
        }
    }

    fun backImage(position: Int) {
        if (isFirstImageOfFirstStory(position)) {
            setImagePosition(0)
        } else if (isFirstImageOfStory(position)) {
            backStory()
        } else {
            setImagePosition(position - 1)
        }
    }

    fun setImagePosition(position: Int) {
        currentImagePositionLiveData.postValue(position)
    }

    private fun nextStory() {
        currentStoryPositionLiveData.postValue(currentStoryPosition + 1)
    }

    private fun backStory() {
        currentStoryPositionLiveData.postValue(currentStoryPosition - 1)
    }

    private fun isFirstImageOfStory(position: Int): Boolean {
        return position == 0
    }

    private fun isFirstImageOfFirstStory(position: Int): Boolean {
        return position == 0 && currentStoryPosition == 0
    }

    private fun isLastImageOfStory(position: Int): Boolean {
        return position == currentStory.images.lastIndex
    }

    private fun isLastImageOfLastStory(position: Int): Boolean {
        return position == currentStory.images.lastIndex && currentStoryPosition == stories.lastIndex
    }


}