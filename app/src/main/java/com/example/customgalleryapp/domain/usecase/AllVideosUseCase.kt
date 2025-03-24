package com.example.customgalleryapp.domain.usecase

import com.example.customgalleryapp.domain.GalleryRepository
import javax.inject.Inject

class AllVideosUseCase @Inject constructor(private val repository: GalleryRepository) {

    suspend fun getAllVideos() = repository.getAllVideos()
}