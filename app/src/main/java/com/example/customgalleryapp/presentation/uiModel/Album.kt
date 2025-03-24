package com.example.customgalleryapp.presentation.uiModel


data class Album(
    val name: String,
    val mediaCount: Int,
    val mediaType: MediaType,
    val iconResource: Int,
    val isAllImages: Boolean = false,
    val isAllVideos: Boolean = false
)
