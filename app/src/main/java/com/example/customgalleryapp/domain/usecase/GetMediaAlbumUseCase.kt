package com.example.customgalleryapp.domain.usecase

import com.example.customgalleryapp.domain.GalleryRepository
import javax.inject.Inject

class GetMediaAlbumUseCase @Inject constructor(private val repository: GalleryRepository) {

    suspend fun getMediaByAlbumName(albumName: String) = repository.getMediaByAlbumName(albumName)
}