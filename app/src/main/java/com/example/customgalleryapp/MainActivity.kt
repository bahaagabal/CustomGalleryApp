package com.example.customgalleryapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.customgalleryapp.presentation.AlbumScreen
import com.example.customgalleryapp.presentation.DetailScreen
import com.example.customgalleryapp.presentation.ExoPlayerScreen
import com.example.customgalleryapp.presentation.detailscreen.DetailsScreen
import com.example.customgalleryapp.presentation.detailscreen.ExoPlayerView
import com.example.customgalleryapp.presentation.galleryScreen.AlbumScreen
import com.example.customgalleryapp.ui.theme.CustomGalleryAppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val permissionsGranted = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= 33) {
                val readMediaImagesGranted =
                    permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false
                val readMediaVideoGranted =
                    permissions[Manifest.permission.READ_MEDIA_VIDEO] ?: false
                permissionsGranted.value = readMediaImagesGranted || readMediaVideoGranted
            } else {
                val readExternalStorageGranted =
                    permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

                permissionsGranted.value = readExternalStorageGranted
            }
        }


        val permissions = if (Build.VERSION.SDK_INT >= 33) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        permissionLauncher.launch(permissions)

        setContent {
            CustomGalleryAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (permissionsGranted.value) {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = AlbumScreen) {
                            composable<AlbumScreen> {
                                AlbumScreen {
                                    navController.navigate(
                                        DetailScreen(
                                            it.name,
                                            it.isAllImages,
                                            it.isAllVideos
                                        )
                                    )
                                }
                            }

                            composable<DetailScreen> {
                                DetailsScreen() {
                                    navController.navigate(ExoPlayerScreen(it))
                                }
                            }

                            composable<ExoPlayerScreen> {
                                val args = it.toRoute<ExoPlayerScreen>()
                                ExoPlayerView(args.uri)
                            }
                        }
                    } else {
                        PermissionDeniedComposable()
                    }
                }
            }
        }
    }

    @Composable
    fun PermissionDeniedComposable() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Permission Denied. Please allow the permissions to access media.",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
