package com.example.customgalleryapp.presentation.galleryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customgalleryapp.domain.usecase.AlbumsUseCase
import com.example.customgalleryapp.presentation.mapper.AlbumUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val useCase: AlbumsUseCase,
    private val mapper: AlbumUiMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(GalleryViewState())
    val state = _state.asStateFlow()
    fun onEvent(intent: GalleryIntent) {
        when (intent) {
            GalleryIntent.LoadAlbums -> loadAlbums()
        }
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            useCase.getAlbums()
                .onEach {
                    _state.value = _state.value.copy(
                        albumsList = mapper.mapToAlbumUiModel(it)
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