package com.example.customgalleryapp.di

import com.example.customgalleryapp.data.GalleryDataSource
import com.example.customgalleryapp.data.GalleryRepositoryImp
import com.example.customgalleryapp.data.mapper.AlbumMapper
import com.example.customgalleryapp.data.mapper.MediaMapper
import com.example.customgalleryapp.domain.GalleryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGalleryRepository(
        dataSource: GalleryDataSource,
        mapper: AlbumMapper,
        mediaMapper: MediaMapper,
    ): GalleryRepository {
        return GalleryRepositoryImp(dataSource, mapper, mediaMapper)
    }
}