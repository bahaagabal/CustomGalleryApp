package com.example.customgalleryapp.domain

import com.example.customgalleryapp.domain.model.AlbumModel
import com.example.customgalleryapp.domain.model.MediaFileModel
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    suspend fun getAlbums(): Flow<List<AlbumModel>>

    suspend fun getAllVideos(): Flow<List<MediaFileModel>>

    suspend fun getAllImages(): Flow<List<MediaFileModel>>

    suspend fun getMediaByAlbumName(albumName: String): Flow<List<MediaFileModel>>
}