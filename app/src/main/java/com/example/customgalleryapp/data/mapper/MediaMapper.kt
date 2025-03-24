package com.example.customgalleryapp.data.mapper

import com.example.customgalleryapp.data.dto.MediaFileDto
import com.example.customgalleryapp.data.dto.MediaTypeDto
import com.example.customgalleryapp.domain.model.MediaFileModel
import com.example.customgalleryapp.domain.model.MediaTypeModel
import javax.inject.Inject

class MediaMapper @Inject constructor() {

    fun mapToMediaModel(mediaDtoList: List<MediaFileDto>) = mediaDtoList.map {
        MediaFileModel(
            id = it.id,
            path = it.path,
            mediaType = if (it.mediaType == MediaTypeDto.IMAGE) MediaTypeModel.IMAGE else MediaTypeModel.VIDEO
        )
    }
}

