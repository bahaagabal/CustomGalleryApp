package com.example.customgalleryapp.domain.usecase

import com.example.customgalleryapp.domain.GalleryRepository
import javax.inject.Inject

class AlbumsUseCase @Inject constructor(private val repository: GalleryRepository) {

    suspend fun getAlbums() = repository.getAlbums()
}