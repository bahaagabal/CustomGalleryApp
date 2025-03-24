package com.example.customgalleryapp.presentation.galleryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.customgalleryapp.R
import com.example.customgalleryapp.presentation.uiModel.Album

@Composable
fun AlbumScreen(
    galleryViewModel: GalleryViewModel = hiltViewModel(),
    onAlbumClicked: (Album) -> Unit
) {

    val state = galleryViewModel.state.collectAsStateWithLifecycle()

    AlbumContent(state.value) {
        onAlbumClicked(it)
    }

    LaunchedEffect(Unit) {
        galleryViewModel.onEvent(GalleryIntent.LoadAlbums)
    }

}

@Composable
private fun AlbumContent(
    state: GalleryViewState,
    onAlbumClicked: (album: Album) -> Unit
) {

    state.error?.let {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    val isGridView = rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(stringResource(R.string.show_in_grid))
            Switch(
                checked = isGridView.value,
                onCheckedChange = { isGridView.value = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isGridView.value) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.albumsList, key = { album -> album.hashCode() }) { item ->
                    AlbumItem(item) {
                        onAlbumClicked(item)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.albumsList, key = { album -> album.hashCode() }) {
                    AlbumItem(it) {
                        onAlbumClicked(it)
                    }
                }
            }
        }
    }
}