package com.example.coolieweather.presentation.fragments.weatherscreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.coolieweather.R
import com.example.coolieweather.buisness.Result
import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.presentation.theme.LAnimation
import com.example.coolieweather.presentation.theme.LoadingImage
import com.example.coolieweather.presentation.theme.WeatherImage

@Composable
fun WeatherScreen(
    currentBackground: Uri?,
    weatherData: Result<WeatherData>?,
    goToCamera: () -> Unit
) {

    Box(
        Modifier.background(
            MaterialTheme.colors.background
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
                    Modifier.fillMaxSize()
                )
            }
            else -> {
                ErrorFetchingWeatherDataView()
            }
        }


        IconButton(
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

        }
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
