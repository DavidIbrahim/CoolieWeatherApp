package com.example.coolieweather.presentation.fragments.gallery

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.RequestOptions
import com.example.coolieweather.R
import com.example.coolieweather.presentation.CoolieWeatherTheme
import com.example.coolieweather.presentation.theme.LoadingImage
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.glide.GlideImage
import timber.log.Timber

@AndroidEntryPoint
class GalleryFragment : Fragment() {


    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CoolieWeatherTheme {
                    GalleryScreen(viewModel) { onBackPressed() }
                }
            }
        }
    }

    private fun onBackPressed() {
        findNavController().navigateUp()
    }


}

@Composable
private fun GalleryScreen(viewModel: GalleryViewModel, onBackPressed: () -> Unit) {
    val images by viewModel.imagesUri.observeAsState()
    Timber.d(images?.size.toString())
    var selectedImage: Uri? by rememberSaveable {
        mutableStateOf(null)
    }
    Scaffold(topBar = {
        TopAppBar() {
            IconButton(onClick = {
                if (selectedImage != null) selectedImage = null
                else onBackPressed()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
            Text(
                text = stringResource(id = R.string.my_images),
                style = MaterialTheme.typography.h6,
                modifier = Modifier

            )
        }
    }) {

        GalleryScreenContent(images, selectedImage) {
            selectedImage = it
        }
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun GalleryScreenContent(
    images: List<Uri>?,
    selectedImage: Uri?,
    onImageClick: (Uri) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        selectedImage.let {
            if (it == null)
                images?.let { PhotoGrid(it, onImageClick) }
            else {
                PreviewImage(it)


            }
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PreviewImage(it: Uri) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        GlideImage(
            data = it.path.toString(),
            contentDescription = "",
            Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGrid(imagesUri: List<Uri>, onImageClick: (Uri) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 70.dp), contentPadding = PaddingValues(10.dp)
    ) {
        items(imagesUri) { imageUri ->
            PhotoItem(imageUri, onImageClick)
        }
    }
}

@Composable
fun PhotoItem(uri: Uri, onImageClick: (Uri) -> Unit) {
    GlideImage(data = uri.path.toString(), contentDescription = "", modifier = Modifier
        .clickable { onImageClick(uri) }
        .padding(1.dp).fillMaxWidth(),
        requestBuilder = {
            val options = RequestOptions()
            options.fitCenter()
            //load only a thumbnail
            options.override(400)
            apply(options)
        },
        loading = { LoadingImage(Modifier.fillMaxSize()) }
    )
}
