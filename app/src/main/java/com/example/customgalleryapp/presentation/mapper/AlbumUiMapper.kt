package com.example.customgalleryapp.presentation.mapper

import com.example.customgalleryapp.R
import com.example.customgalleryapp.domain.model.AlbumModel
import com.example.customgalleryapp.domain.model.MediaTypeModel
import com.example.customgalleryapp.presentation.uiModel.Album
import com.example.customgalleryapp.presentation.uiModel.MediaType
import javax.inject.Inject

class AlbumUiMapper @Inject constructor() {

    fun mapToAlbumUiModel(albumModel: List<AlbumModel>) = albumModel.map {
        Album(
            name = it.name,
            mediaCount = it.mediaCount,
            mediaType = if (it.mediaTypeModel == MediaTypeModel.IMAGE) MediaType.IMAGE else MediaType.VIDEO,
            iconResource = if (it.mediaTypeModel == MediaTypeModel.IMAGE) R.drawable.ic_image else R.drawable.ic_video,
            isAllImages = it.isAllImages,
            isAllVideos = it.isAllVideos
        )
    }
}