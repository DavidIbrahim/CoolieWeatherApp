package com.example.coolieweather.presentation.fragments.weatherscreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.coolieweather.R
import com.example.coolieweather.buisness.models.Result
import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.presentation.BottomSheetShape
import com.example.coolieweather.presentation.theme.*

@ExperimentalMaterialApi
@Composable
fun WeatherScreen(
    currentBackground: Uri?,
    stillSearchingForLocation: Boolean,
    weatherData: Result<WeatherData>?,
    goToCamera: () -> Unit,
    goToGallery: () -> Unit,
    saveImageInDatabase: (Uri) -> Unit,
    shareImage: () -> Unit,
    reloadWeatherData: () -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(colors.surface, BottomSheetShape)
                    .padding(top = 65.dp),

                ) {
                Column(
                    Modifier
                        .fillMaxWidth(),
                ) {
                    BottomSheetOptionRow(
                        R.drawable.gallery,
                        stringResource(id = R.string.open_saved_images),
                        goToGallery
                    )
                    Divider()
                    BottomSheetOptionRow(
                        R.drawable.ic_share,
                        stringResource(id = R.string.open_saved_images),
                        shareImage
                    )

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
            WeatherContent(
                currentBackground,
                weatherData,
                stillSearchingForLocation,
                saveImageInDatabase,
                reloadWeatherData
            )
        }
    }
}

@Composable
fun BottomSheetOptionRow(iconResourceID: Int, text: String, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(vertical = 10.dp, horizontal = 16.dp)) {
        Image(
            painter = painterResource(id = iconResourceID),
            contentDescription = "", Modifier.size(40.dp)
        )
        Text(text = text, Modifier.padding(start = 16.dp))
    }
}

@Composable
private fun WeatherContent(
    currentBackground: Uri?,
    weatherData: Result<WeatherData>?,
    stillSearchingForLocation: Boolean,
    saveImageInDatabase: (Uri) -> Unit,
    reloadWeatherData: () -> Unit
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(
                colors.background
            )
    ) {
        if (stillSearchingForLocation) {
            SearchingLocationScreen(Modifier.align(Alignment.Center))
        } else if (currentBackground == null) {
            NoBackgroundScreen(Modifier.align(Alignment.Center))
        } else if (weatherData == null) {
            LoadingImage(
                Modifier
                    .align(Alignment.Center), true
            )
        } else if (weatherData is Result.Success) {
            WeatherImage(
                picURL = currentBackground.path.toString(),
                weatherData.data,
                saveImageInDatabase,
                Modifier.fillMaxSize()
            )
        } else {
            ErrorFetchingWeatherDataView(Modifier.align(Alignment.Center),reloadWeatherData)
        }


    }
}

@Composable
fun SearchingLocationScreen(modifier: Modifier) {
    Column(modifier, Arrangement.Center, Alignment.CenterHorizontally) {
        LAnimation(rawResourceID = R.raw.location_searching, Modifier.requiredSize(300.dp))
        Text(
            text = stringResource(id = R.string.searching_location_msg),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(50.dp),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun ErrorFetchingWeatherDataView(modifier: Modifier, reloadWeatherData: () -> Unit) {
    Column(modifier, Arrangement.Center, Alignment.CenterHorizontally) {
        LAnimation(rawResourceID = R.raw.error_network, Modifier.requiredSize(300.dp))
        Button(
            onClick = reloadWeatherData,
            modifier = Modifier.padding(30.dp),
            //this is the shadow effect
            colors = ButtonDefaults.buttonColors(backgroundColor = gray400,contentColor = Color.White),
            contentPadding = PaddingValues(15.dp, 10.dp, 15.dp, 10.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 1.dp),
            shape = RoundedCornerShape(5.dp)
        ){
            Text(text = stringResource(id = R.string.reload))
        }


    }


}

@Composable
fun NoBackgroundScreen(modifier: Modifier) {
    Column(
        modifier
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
