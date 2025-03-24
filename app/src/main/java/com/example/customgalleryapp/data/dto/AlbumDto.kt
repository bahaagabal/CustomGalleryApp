package com.example.customgalleryapp.data.dto

import androidx.annotation.Keep

@Keep
data class AlbumDto(
    val name: String,
    val mediaCount: Int,
    val mediaType: MediaTypeDto,
    val isAllImages: Boolean = false,
    val isAllVideos: Boolean = false,
)
