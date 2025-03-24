package com.example.customgalleryapp.domain.model

data class AlbumModel(
    val name: String,
    val mediaCount: Int,
    val mediaTypeModel: MediaTypeModel,
    val isAllImages: Boolean = false,
    val isAllVideos: Boolean = false
)