package com.example.customgalleryapp.presentation.mapper

import com.example.customgalleryapp.domain.model.MediaFileModel
import com.example.customgalleryapp.domain.model.MediaTypeModel
import com.example.customgalleryapp.presentation.uiModel.MediaFile
import com.example.customgalleryapp.presentation.uiModel.MediaType
import javax.inject.Inject

class MediaFileUiMapper @Inject constructor() {
    fun mapToMediaUiModel(mediaFileList: List<MediaFileModel>) = mediaFileList.map {
        MediaFile(
            id = it.id,
            path = it.path,
            mediaType = if (it.mediaType == MediaTypeModel.IMAGE) MediaType.IMAGE else MediaType.VIDEO,
        )
    }
}