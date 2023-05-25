package com.dinhpx.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dinhpx.test.model.Story
import com.dinhpx.test.model.TabEntity

class MainViewModel : ViewModel() {
    var isStopFragment: Boolean = false


    var currentStoryPosition = 0
    var currentStoryPositionLiveData = MutableLiveData<Int>()
    val stories = mutableListOf<Story>()
    val currentStory: Story
        get() = stories[currentStoryPosition]


    val imageLiveData = MutableLiveData<Int>()


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



    fun nextImage() {
        if (isLastImageOfLastStory()) {
            updateImagePosition(currentStory.currentImagePosition)
        } else if (isLastImageOfStory()) {
            nextStory()
        } else {
            updateImagePosition(currentStory.currentImagePosition + 1)
        }
    }

    fun backImage() {
        if (isFirstImageOfFirstStory()) {
            updateImagePosition(0)
        } else if (isFirstImageOfStory()) {
            backStory()
        } else {
            updateImagePosition(currentStory.currentImagePosition - 1)
        }
    }

    fun updateImagePosition(position: Int) {
        currentStory.currentImagePosition = position
        imageLiveData.postValue(currentStory.images[currentStory.currentImagePosition])
    }

    private fun nextStory() {
        currentStoryPositionLiveData.postValue(currentStoryPosition + 1)
    }

    private fun backStory() {
        currentStoryPositionLiveData.postValue(currentStoryPosition - 1)
    }

    private fun isFirstImageOfStory(): Boolean {
        return currentStory.currentImagePosition == 0
    }

    private fun isFirstImageOfFirstStory(): Boolean {
        return currentStory.currentImagePosition == 0 && currentStoryPosition == 0
    }

    private fun isLastImageOfStory(): Boolean {
        return currentStory.currentImagePosition == currentStory.images.lastIndex
    }

    private fun isLastImageOfLastStory(): Boolean {
        return currentStory.currentImagePosition == currentStory.images.lastIndex && currentStoryPosition == stories.lastIndex
    }


}