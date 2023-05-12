package com.dinhpx.test

import androidx.lifecycle.ViewModel
import com.dinhpx.test.data.StoryModel

class MainViewModel : ViewModel() {


    var isRestartStory: Boolean = false

    var previousImagePosition = 0
    var currentImagePosition = 0

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
        stories.add(StoryModel("3", colors.shuffled().takeLast(1)))

        val a = 0

    }

    fun canBackPrevStory(): Boolean {
        return currentImagePosition < 0 && currentStoryPosition > 0
    }

    fun canGoToNextStory(): Boolean {
        return currentImagePosition > currentStory.images.lastIndex && currentStoryPosition < stories.lastIndex
    }

    fun isOutLastImageOfLastStory(): Boolean {
        return currentStoryPosition == stories.lastIndex && currentImagePosition > currentStory.images.lastIndex
    }

    fun isOutFirstImageOfFirstStory(): Boolean {
        return currentStoryPosition == 0 && currentImagePosition < 0
    }
}