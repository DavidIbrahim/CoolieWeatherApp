package com.example.coolieweather.presentation.fragments.weatherscreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.coolieweather.R
import com.example.coolieweather.buisness.models.Result
import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.presentation.BottomSheetShape
import com.example.coolieweather.presentation.theme.LAnimation
import com.example.coolieweather.presentation.theme.LoadingImage
import com.example.coolieweather.presentation.theme.WeatherImage

@ExperimentalMaterialApi
@Composable
fun WeatherScreen(
    currentBackground: Uri?,
    weatherData: Result<WeatherData>?,
    goToCamera: () -> Unit,
    goToGallery: () -> Unit,
    saveImageInDatabase:(Uri)->Unit
) {

    val scope = rememberCoroutineScope()
    val selection = remember { mutableStateOf(1) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(colors.surface, BottomSheetShape)
                    .padding(top=65.dp),

                ) {
                Column(
                    Modifier
                        .fillMaxWidth(),
                ) {
                    BottomSheetOptionRow(R.drawable.gallery,stringResource(id = R.string.open_saved_images),goToGallery)
                    Divider()

                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToCamera
            ) {
                Icon(Icons.Default.Camera, contentDescription = "Open Camera")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = scaffoldState,

        sheetPeekHeight = 60.dp,

        ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            WeatherContent(currentBackground, weatherData,saveImageInDatabase)
        }
    }
}

@Composable
fun BottomSheetOptionRow(iconResourceID:Int,text: String,onClick: () -> Unit, ) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }.padding(vertical = 10.dp,horizontal = 16.dp)) {
        Image(
             painter = painterResource(id = iconResourceID),
            contentDescription = "",Modifier.size(40.dp)
        )
        Text(text = text, Modifier.padding(start = 16.dp))
    }
}

@Composable
private fun WeatherContent(
    currentBackground: Uri?,
    weatherData: Result<WeatherData>?,
    saveImageInDatabase:(Uri)->Unit
) {

    Box(
        Modifier.background(
            colors.background
        )
    ) {
        when {
            currentBackground == null -> NoBackgroundScreen(Modifier.align(Alignment.Center))
            weatherData == null -> {
                LoadingImage(Modifier.size(100.dp))

            }
            weatherData is Result.Success -> {
                WeatherImage(
                    picURL = currentBackground.path.toString(),
                    weatherData.data,
                    saveImageInDatabase,
                    Modifier.fillMaxSize()
                )
            }
            else -> {
                ErrorFetchingWeatherDataView()
            }
        }


        /* IconButton(
             onClick = goToCamera,
             Modifier
                 .padding(16.dp)

                 .background(
                     MaterialTheme.colors.secondary,
                     CircleShape
                 )
                 .align(
                     Alignment.BottomEnd
                 )
         ) {
             Icon(
                 painter = painterResource(R.drawable.ic_baseline_camera_alt_24),
                 contentDescription = "Open Camera",
                 tint = Color.White
             )

         }*/
    }
}

@Composable
fun ErrorFetchingWeatherDataView() {

}

@Composable
fun NoBackgroundScreen(modifier: Modifier) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(65.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LAnimation(R.raw.insta_camera, Modifier.requiredSize(150.dp), Int.MAX_VALUE)
        Text(
            text = stringResource(id = R.string.add_image),
            Modifier.padding(vertical = 20.dp),
            textAlign = TextAlign.Center
        )
    }
}
