package com.dinhpx.test.model

data class Story(
    val name: String,
    val images: List<Int>,
) {
    var currentImagePosition: Int = 0
        set(value) {
            field = if (value < 0) 0
            else if (value > images.lastIndex) images.lastIndex
            else value
        }
}