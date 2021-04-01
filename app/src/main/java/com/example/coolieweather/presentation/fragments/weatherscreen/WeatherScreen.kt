package com.example.coolieweather.presentation.fragments.weatherscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.coolieweather.R

@Composable
fun WeatherScreen(goToCamera: ()->Unit) {
    Box(
        Modifier.background(
            Brush.linearGradient(
                listOf(
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.primaryVariant
                )
            )
        )
    ) {

        IconButton(onClick = goToCamera, Modifier.background(MaterialTheme.colors.secondary).padding(16.dp).align(
            Alignment.BottomEnd)) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_camera_alt_24),
                contentDescription = "Open Camera"
            )

        }
    }
}