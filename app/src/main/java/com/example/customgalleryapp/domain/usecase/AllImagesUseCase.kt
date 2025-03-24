package com.example.customgalleryapp.domain.usecase

import com.example.customgalleryapp.domain.GalleryRepository
import javax.inject.Inject

class AllImagesUseCase @Inject constructor(private val repository: GalleryRepository) {

    suspend fun getAlImages() = repository.getAllImages()
}