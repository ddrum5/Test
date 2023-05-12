package com.dinhpx.test.data

data class StoryModel(
    val name: String,
    val images: List<Int>,
) {
    var currentPosition: Int = 0
}