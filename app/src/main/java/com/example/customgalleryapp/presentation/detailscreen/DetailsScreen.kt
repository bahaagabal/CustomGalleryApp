package com.example.customgalleryapp.presentation.detailscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.customgalleryapp.presentation.uiModel.MediaType

@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    onVideoItemClicked: (String) -> Unit
) {
    val state = detailsViewModel.state.collectAsStateWithLifecycle()

    DetailsContent(state.value) {
        onVideoItemClicked(it)
    }

    LaunchedEffect(Unit) {
        detailsViewModel.onEvent(DetailsIntent.OnInitDetailsScreen)
    }
}

@Composable
private fun DetailsContent(
    state: DetailsViewUiState,
    onVideoItemClicked: (String) -> Unit
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

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.mediaList, key = { media -> media.hashCode() }) { item ->

            when (item.mediaType) {
                MediaType.IMAGE -> ImageItem(item.path)
                MediaType.VIDEO -> VideoPlayerItem(item.path , item.id) {
                    onVideoItemClicked(it)
                }
            }
        }
    }
}