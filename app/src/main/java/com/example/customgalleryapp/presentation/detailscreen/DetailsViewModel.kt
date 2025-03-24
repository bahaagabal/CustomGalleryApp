package com.example.customgalleryapp.presentation.detailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.customgalleryapp.domain.usecase.AllImagesUseCase
import com.example.customgalleryapp.domain.usecase.AllVideosUseCase
import com.example.customgalleryapp.domain.usecase.GetMediaAlbumUseCase
import com.example.customgalleryapp.presentation.DetailScreen
import com.example.customgalleryapp.presentation.mapper.MediaFileUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val allImagesUseCase: AllImagesUseCase,
    private val allVideosUseCase: AllVideosUseCase,
    private val getMediaAlbumUseCase: GetMediaAlbumUseCase,
    private val mediaFileUiMapper: MediaFileUiMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val args = savedStateHandle.toRoute<DetailScreen>()

    private val _state = MutableStateFlow(DetailsViewUiState())
    val state = _state.asStateFlow()

    fun onEvent(intent: DetailsIntent) {
        when (intent) {
            DetailsIntent.OnInitDetailsScreen -> when {
                args.isAllImages -> loadAllImages()

                args.isAllVideos -> loadAllVideos()

                else -> loadAlbumMediaDetails()
            }
        }
    }

    private fun loadAllImages() {
        viewModelScope.launch {
            allImagesUseCase.getAlImages()
                .onEach {
                    _state.value = _state.value.copy(
                        mediaList = mediaFileUiMapper.mapToMediaUiModel(it)
                    )
                }.catch {
                    _state.value = _state.value.copy(
                        error = it.message
                    )
                }.flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    private fun loadAllVideos() {
        viewModelScope.launch {
            allVideosUseCase.getAllVideos()
                .onEach {
                    _state.value = _state.value.copy(
                        mediaList = mediaFileUiMapper.mapToMediaUiModel(it)
                    )
                }.catch {
                    _state.value = _state.value.copy(
                        error = it.message
                    )
                }.flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    private fun loadAlbumMediaDetails() {
        viewModelScope.launch {
            getMediaAlbumUseCase.getMediaByAlbumName(args.albumName)
                .onEach {
                    _state.value = _state.value.copy(
                        mediaList = mediaFileUiMapper.mapToMediaUiModel(it)
                    )
                }.catch {
                    _state.value = _state.value.copy(
                        error = it.message
                    )
                }.flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }
}