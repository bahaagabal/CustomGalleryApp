package com.example.customgalleryapp.presentation.galleryScreen

import com.example.customgalleryapp.presentation.uiModel.Album

sealed class GalleryIntent {
    data object LoadAlbums : GalleryIntent()
}

data class GalleryViewState(
    val albumsList: List<Album> = emptyList(),
    val error: String? = null,
)