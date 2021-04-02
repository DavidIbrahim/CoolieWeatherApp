package com.example.coolieweather.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.coolieweather.presentation.theme.gray100
import com.example.coolieweather.presentation.theme.gray400
import com.example.coolieweather.presentation.theme.orange100
import com.example.coolieweather.presentation.theme.orange400


private val LightColors = lightColors(
    primary = orange100,
    primaryVariant = orange400,
    onPrimary = Color.White,
    secondary = orange400,
    surface = Color.White, background = gray100,
    onSurface = gray400,
    onSecondary = Color.White
)

@Composable
fun CoolieWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        //todo change this to dark colors when designed
        LightColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}