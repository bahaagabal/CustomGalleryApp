package com.example.customgalleryapp.presentation.detailscreen

import com.example.customgalleryapp.presentation.uiModel.MediaFile

sealed class DetailsIntent {
    data object OnInitDetailsScreen : DetailsIntent()
}

data class DetailsViewUiState(
    val mediaList: List<MediaFile> = emptyList(),
    val error: String? = null,
)
