package com.example.coolieweather.presentation.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.res.ResourcesCompat
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.coolieweather.R.font
import com.example.coolieweather.buisness.Result
import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.presentation.utils.*
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
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillBounds,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    transform: ((Bitmap) -> Bitmap)? = null
) {
    Timber.d(weatherData.toString())
    Box(modifier) {
        val imageState: UIState<Bitmap> = loadPicture(url = picURL)


        when (imageState) {
            //todo change error view
            is Error -> LoadingImage(Modifier.matchParentSize())
            Loading -> LoadingImage(Modifier.matchParentSize())
            is Success -> {
                val displayedImage = imageState.data.let {
                    writeWeatherDataOnImage(it, weatherData, LocalContext.current)
                }
                androidx.compose.foundation.Image(
                    bitmap = displayedImage.asImageBitmap(),
                    contentDescription = null,
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

fun writeWeatherDataOnImage(bitmap: Bitmap, weatherData: WeatherData, context: Context): Bitmap {
    val newBitmap = bitmap.copy(bitmap.config, true);
    val canvas = Canvas(newBitmap)
    val canvasHeight = canvas.height
    val canvasWidth = canvas.width
    val temperatureYPos = canvasHeight / 7
    val placeNameYPos = (canvasHeight / 1.2).toInt()
    val placeNameXPos = canvasWidth
    val temperatureXPos = 0
    val padding = 30
    val temperatureTextSize = 120
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
        40,
        boldTextStyle = true,
        temperatureXPos, temperatureYPos + temperatureTextSize * 2, padding
    )
    writeDataOnImage(
        canvas,
        (context.getCityName(weatherData.geoPoint) as Result.Success).data,
        context,
        50,
        boldTextStyle = true,
        placeNameXPos, placeNameYPos, -padding, Align.RIGHT
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

/*

    val xPos = canvas.width / 2 - 2 //-2 is for regulating the x position offset


    //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.

    //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
    val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()) / 3).toInt()
*/

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
fun LoadingImage(modifier: Modifier = Modifier) {
    Box(modifier) {
        CircularProgressIndicator(
            Modifier
                .align(Alignment.Center)
        )
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