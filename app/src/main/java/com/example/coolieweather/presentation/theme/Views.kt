package com.example.coolieweather.presentation.theme

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.coolieweather.presentation.utils.Error
import com.example.coolieweather.presentation.utils.Loading
import com.example.coolieweather.presentation.utils.Success
import com.example.coolieweather.presentation.utils.UIState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.coolieweather.buisness.models.WeatherData
import android.graphics.Paint.Align
import timber.log.Timber


@Composable
fun LAnimation(rawResourceID: Int, modifier: Modifier = Modifier, repeatCount:Int=1) {
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
                   writeDataOnImage(it,weatherData.weatherDescription, LocalContext.current)
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

fun writeDataOnImage(bitmap: Bitmap, text: String,context:Context): Bitmap {
    val tf = Typeface.create("Helvetica", Typeface.DEFAULT.style)

    val paint = Paint()
    paint.style = Paint.Style.FILL
    paint.color = Color.WHITE
    paint.typeface = tf
    paint.textAlign = Align.CENTER
    paint.textSize = convertToPixels(context, 11).toFloat()

    val textRect = Rect()
    paint.getTextBounds(text, 0, text.length, textRect)

    val canvas = Canvas(bitmap)

    //If the text is bigger than the canvas , reduce the font size

    //If the text is bigger than the canvas , reduce the font size
    if (textRect.width() >= canvas.width - 4) //the padding on either sides is considered as 4, so as to appropriately fit in the text
        paint.textSize = convertToPixels(context , 7).toFloat() //Scaling needs to be used for different dpi's
return bitmap

}

fun convertToPixels(context: Context, nDP: Int): Int {
    val conversionScale = context.resources.displayMetrics.density
    return (nDP * conversionScale + 0.5f).toInt()
}

@Composable
fun LoadingImage(modifier: Modifier= Modifier) {
    Box(modifier) {
        CircularProgressIndicator(Modifier
            .align(Alignment.Center))
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