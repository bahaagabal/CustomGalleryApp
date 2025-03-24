package com.example.customgalleryapp.data.mapper

import com.example.customgalleryapp.data.dto.AlbumDto
import com.example.customgalleryapp.data.dto.MediaTypeDto
import com.example.customgalleryapp.domain.model.AlbumModel
import com.example.customgalleryapp.domain.model.MediaTypeModel
import javax.inject.Inject

class AlbumMapper @Inject constructor() {

    fun mapToAlbumModel(albumDto: List<AlbumDto>) =
        albumDto.map {
            AlbumModel(
                name = it.name,
                mediaCount = it.mediaCount,
                mediaTypeModel = if (it.mediaType == MediaTypeDto.IMAGE) MediaTypeModel.IMAGE else MediaTypeModel.VIDEO,
                isAllImages = it.isAllImages,
                isAllVideos = it.isAllVideos
            )
        }
}