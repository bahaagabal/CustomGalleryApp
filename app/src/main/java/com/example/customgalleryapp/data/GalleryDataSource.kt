package com.example.customgalleryapp.data

import android.content.Context
import android.provider.MediaStore
import com.example.customgalleryapp.data.dto.AlbumDto
import com.example.customgalleryapp.data.dto.MediaFileDto
import com.example.customgalleryapp.data.dto.MediaTypeDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun getAlbums(): List<AlbumDto> = withContext(Dispatchers.IO) {

        val imageAlbums = getMediaCountByType(MediaTypeDto.IMAGE)
        addAllImages(imageAlbums)


        val videoAlbums = getMediaCountByType(MediaTypeDto.VIDEO)
        addAllVideos(videoAlbums)

        return@withContext imageAlbums + videoAlbums
    }

    private fun addAllImages(imageAlbums: MutableList<AlbumDto>): MutableList<AlbumDto> {

        if (imageAlbums.isNotEmpty()) {
            imageAlbums.add(
                AlbumDto(
                    "All Images",
                    imageAlbums.sumOf { it.mediaCount },
                    MediaTypeDto.IMAGE,
                    isAllImages = true
                )
            )
        }

        return imageAlbums
    }

    private fun addAllVideos(videoAlbums: MutableList<AlbumDto>): MutableList<AlbumDto> {
        if (videoAlbums.isNotEmpty()) {
            videoAlbums.add(
                AlbumDto(
                    "All Videos",
                    videoAlbums.sumOf { it.mediaCount },
                    MediaTypeDto.VIDEO,
                    isAllVideos = true
                )
            )
        }

        return videoAlbums
    }

    private suspend fun getMediaCountByType(mediaType: MediaTypeDto): MutableList<AlbumDto> {
        return withContext(Dispatchers.IO) {
            val albums = mutableListOf<AlbumDto>()
            val albumMap = mutableMapOf<String, Int>()


            val uri = when (mediaType) {
                MediaTypeDto.IMAGE -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                MediaTypeDto.VIDEO -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

            val projection = arrayOf(
                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.BUCKET_DISPLAY_NAME
            )

            val selection = "${MediaStore.MediaColumns.DATA} NOT LIKE ?"
            val selectionArgs = arrayOf("%cache%")

            val cursor = context.contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs,
                null
            )

            cursor?.use {
                val dataColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                val albumColumn =
                    it.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)

                while (it.moveToNext()) {
                    val filePath = it.getString(dataColumn)
                    val albumName = it.getString(albumColumn)
                    albumMap[albumName] = albumMap.getOrDefault(albumName, 0) + 1
                }
            }

            albumMap.forEach { (albumName, mediaCount) ->
                albums.add(AlbumDto(albumName, mediaCount, mediaType))
            }

            albums
        }
    }

    fun getAllImages(): List<MediaFileDto> {
        val mediaFiles = mutableListOf<MediaFileDto>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        val selection = "${MediaStore.Images.Media.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf("image/%")

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                addMediaItem(path, mediaFiles, id, MediaTypeDto.IMAGE)
            }
        }

        return mediaFiles
    }

    fun getAllVideos(): List<MediaFileDto> {
        val mediaFiles = mutableListOf<MediaFileDto>()

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        )

        val selection = "${MediaStore.Video.Media.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf("video/%")

        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                addMediaItem(path, mediaFiles, id, MediaTypeDto.VIDEO)
            }
        }

        return mediaFiles
    }

    fun getMediaByAlbum(albumName: String): List<MediaFileDto> {
        val mediaFiles = mutableListOf<MediaFileDto>()

        val imageSelection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val videoSelection = "${MediaStore.Video.Media.BUCKET_DISPLAY_NAME} = ?"

        val selectionArgs = arrayOf(albumName)

        val imageCursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            ),
            imageSelection,
            selectionArgs,
            null
        )

        imageCursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                addMediaItem(path, mediaFiles, id, MediaTypeDto.IMAGE)
            }
        }

        val videoCursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME
            ),
            videoSelection,
            selectionArgs,
            null
        )

        videoCursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))

                addMediaItem(path, mediaFiles, id, MediaTypeDto.VIDEO)
            }
        }

        return mediaFiles
    }

    private fun addMediaItem(
        path: String,
        mediaFiles: MutableList<MediaFileDto>,
        id: String,
        mediaType: MediaTypeDto
    ) {

        if (!path.contains("thumbnails") && !path.contains("cache")) {
            mediaFiles.add(
                MediaFileDto(id, path, mediaType)
            )
        }
    }
}