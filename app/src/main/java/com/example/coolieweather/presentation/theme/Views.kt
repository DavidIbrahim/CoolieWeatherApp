package com.example.coolieweather.presentation.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.coolieweather.R
import com.example.coolieweather.R.font
import com.example.coolieweather.buisness.models.Result
import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.presentation.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun LAnimation(rawResourceID: Int, modifier: Modifier = Modifier, repeatCount: Int = 1) {
    val animationSpec = remember { LottieAnimationSpec.RawRes(rawResourceID) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = repeatCount)
    LottieAnimation(
        animationSpec,
        modifier = modifier,
        animationState
    )


}


@Composable
fun WeatherImage(
    picURL: String,
    weatherData: WeatherData,
    saveImageInDatabase: (Uri) -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillBounds,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    Timber.d(weatherData.toString())
    Box(modifier) {
        val imageState: UIState<Bitmap> = loadPicture(url = picURL)
        when (imageState) {
            is Loading -> LoadingImage(Modifier.matchParentSize(), true)
            is Success -> {
                SaveImageAndShow(
                    picURL,
                    imageState,
                    weatherData,
                    saveImageInDatabase,
                    modifier,
                    alignment,
                    contentScale,
                    alpha,
                    colorFilter
                )

            }
        }
    }
}

@Composable
private fun SaveImageAndShow(
    picURL: String,
    imageState: Success<Bitmap>,
    weatherData: WeatherData,
    saveImageInDatabase: (Uri) -> Unit,
    modifier: Modifier,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    colorFilter: ColorFilter?
) {
    val context = LocalContext.current
    var isImageSaved by rememberSaveable(key = picURL) { mutableStateOf(false) }
    var displayedImage: Bitmap? = rememberSaveable(key = picURL) {

        val weatherImage: Bitmap =
            writeWeatherDataOnImage(imageState.data, weatherData, context)

        GlobalScope.launch {
            val uri = context.writeContentToShareableFile(weatherImage)
            saveImageInDatabase(uri)
            isImageSaved = true
        }
        weatherImage
    }
    if (isImageSaved)
        Image(
            bitmap = displayedImage!!.asImageBitmap(),
            contentDescription = null,
            modifier,
            alignment,
            contentScale,
            alpha,
            colorFilter
        )
    else LoadingImage(Modifier.fillMaxSize(), true)
}

fun writeWeatherDataOnImage(bitmap: Bitmap, weatherData: WeatherData, context: Context): Bitmap {
    val newBitmap = bitmap.copy(bitmap.config, true);
    val canvas = Canvas(newBitmap)
    val canvasHeight = canvas.height
    val temperatureYPos = canvasHeight / 7
    val placeNameYPos = (canvasHeight / 1.1).toInt()
    val placeNameXPos = 0
    val temperatureXPos = 0
    val padding = 30
    val temperatureTextSize = 120
    val weatherDescriptionTextSize = 40
    val weatherDescriptionYPos =temperatureYPos+temperatureTextSize * 2
    val windSpeedYPos =weatherDescriptionYPos+150
    val windSpeedTextSize= 35
    val humidityYPos = windSpeedYPos+150
    val humidityTextSize = 35
    writeDataOnImage(
        canvas,
        weatherData.temp.toString() + "\u2103",
        context,
        temperatureTextSize,
        boldTextStyle = true,
        temperatureXPos, temperatureYPos, padding
    )
    writeDataOnImage(
        canvas,
        weatherData.weatherDescription,
        context,
        weatherDescriptionTextSize,
        boldTextStyle = true,
        temperatureXPos, temperatureYPos + temperatureTextSize * 2, padding
    )

    writeDataOnImage(
        canvas,
        context.getString(R.string.wind_speed_value,weatherData.windSpeed),
        context,
        windSpeedTextSize,
        boldTextStyle = false,
        temperatureXPos,  windSpeedYPos, padding, Align.LEFT
    )

    writeDataOnImage(
        canvas,
        context.getString(R.string.humidity_value,weatherData.humidity.toInt()),
        context,
        humidityTextSize,
        boldTextStyle = false,
        temperatureXPos, humidityYPos, padding, Align.LEFT
    )

    writeDataOnImage(
        canvas,
        (context.getCityName(weatherData.geoPoint) as Result.Success).data,
        context,
        50,
        boldTextStyle = true,
        placeNameXPos, placeNameYPos, padding, Align.LEFT
    )
    return newBitmap
}

fun writeDataOnImage(
    canvas: Canvas,
    text: String,
    context: Context,
    textSize: Int = 100,
    boldTextStyle: Boolean = true,
    xPos: Int,
    yPos: Int,
    padding: Int,
    align: Paint.Align = Align.LEFT
) {
    Timber.d("writing data on image")
    val textStyleResourceID = if (boldTextStyle) font.lato_bold else font.lato

    val tf = ResourcesCompat.getFont(context, textStyleResourceID)
    var bodyTextColor = Color.WHITE

    /*  //todo use palette to get text color
      Palette.from(bitmap).generate { palette: Palette? ->
          (palette?.vibrantSwatch?.bodyTextColor ?: Color.WHITE)

      }
  */
    val paint = Paint()
    paint.style = Paint.Style.FILL
    paint.color = bodyTextColor
    paint.typeface = tf
    paint.textAlign = align
    paint.textSize = convertToPixels(context, textSize).toFloat()
    paint.isAntiAlias = true;


    canvas.drawText(
        text,
        xPos.toFloat() + convertToPixels(context, padding),
        yPos.toFloat() + convertToPixels(context, padding),
        paint
    )

}


fun convertToPixels(context: Context, nDP: Int): Int {
    val conversionScale = context.resources.displayMetrics.density
    return (nDP * conversionScale + 0.5f).toInt()
}

@Composable
fun LoadingImage(modifier: Modifier = Modifier, animated: Boolean = false) {
    if (animated) {
        LAnimation(rawResourceID = R.raw.loading_animation, modifier, Int.MAX_VALUE)
    } else {
        Box(modifier) {
            CircularProgressIndicator(
                modifier
                    .align(Alignment.Center)
            )
        }
    }

}

@Composable
fun loadPicture(url: String): UIState<Bitmap> {
    var bitmapState: UIState<Bitmap> by remember { mutableStateOf(Loading) }

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState = Success(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    return bitmapState
}