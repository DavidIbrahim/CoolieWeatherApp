package com.example.coolieweather.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coolieweather.presentation.theme.*


private val LightColors = lightColors(
    primary = green100,
    primaryVariant = green400,
    onPrimary = Color.White,
    secondary = green400,
    surface = Color.White, background = gray100,
    onSurface = gray400,
    onSecondary = Color.White
)
val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomEnd = 0.dp,
    bottomStart = 0.dp
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