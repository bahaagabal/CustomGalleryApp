package com.example.customgalleryapp.data

import com.example.customgalleryapp.data.mapper.AlbumMapper
import com.example.customgalleryapp.data.mapper.MediaMapper
import com.example.customgalleryapp.domain.GalleryRepository
import com.example.customgalleryapp.domain.model.MediaFileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GalleryRepositoryImp @Inject constructor(
    private val dataSource: GalleryDataSource,
    private val mapper: AlbumMapper,
    private val mediaMapper: MediaMapper,
) : GalleryRepository {

    override suspend fun getAlbums() = flow {
        val albums = dataSource.getAlbums()
        emit(mapper.mapToAlbumModel(albums))
    }

    override suspend fun getAllVideos(): Flow<List<MediaFileModel>> = flow {
        val allVideos = dataSource.getAllVideos()
        emit(mediaMapper.mapToMediaModel(allVideos))
    }

    override suspend fun getAllImages(): Flow<List<MediaFileModel>> = flow {
        val allImages = dataSource.getAllImages()
        emit(mediaMapper.mapToMediaModel(allImages))
    }

    override suspend fun getMediaByAlbumName(albumName: String): Flow<List<MediaFileModel>> = flow {
        val mediaList = dataSource.getMediaByAlbum(albumName)
        emit(mediaMapper.mapToMediaModel(mediaList))
    }
}