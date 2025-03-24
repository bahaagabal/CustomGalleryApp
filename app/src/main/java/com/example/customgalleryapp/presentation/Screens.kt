package com.example.customgalleryapp.presentation

import android.net.Uri
import kotlinx.serialization.Serializable


@Serializable
data object AlbumScreen

@Serializable
data class DetailScreen(
    val albumName: String,
    val isAllImages: Boolean = false,
    val isAllVideos: Boolean = false
)

@Serializable
data class ExoPlayerScreen(val uri: String)