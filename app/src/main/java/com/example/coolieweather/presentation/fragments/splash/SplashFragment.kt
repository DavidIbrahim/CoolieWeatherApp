package com.example.coolieweather.presentation.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coolieweather.R
import com.example.coolieweather.presentation.CoolieWeatherTheme
import com.example.coolieweather.presentation.theme.LAnimation
import com.example.coolieweather.presentation.theme.green100
import com.example.coolieweather.presentation.theme.green400
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            lifecycleScope.launchWhenStarted {
                delay(4000)
                goToNextScreen()
            }
            setContent {
                CoolieWeatherTheme {
                    SplashScreen()
                }
            }
        }
    }

    private fun goToNextScreen() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToWeatherFragment())
    }


}

@Composable
private fun SplashScreen() {
    Box(Modifier.background(Brush.linearGradient(listOf(green100, green400)))) {
        LAnimation(
            rawResourceID = R.raw.day_night,
            Modifier
                .align(Alignment.Center)
                .requiredSize(150.dp)
        )
        Text(
            text = stringResource(id = R.string.coolie_weather),
            style = MaterialTheme.typography.h4,
            color = Color.White,
            fontFamily = FontFamily(
                Font(R.font.lilyscipt)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(60.dp)
        )
    }

}



