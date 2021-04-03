package com.example.coolieweather.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toUri
import com.example.coolieweather.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


suspend fun Context.writeContentToShareableFile(bitmap: Bitmap): Uri {


    return withContext(Dispatchers.IO) {
        val file = createImageFile()
        val fOut = FileOutputStream(file)
        //since saved images won't be used as background again reduce quality
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, fOut)
        return@withContext file.toUri()

    }

}

@SuppressLint("SimpleDateFormat")
@Throws(IOException::class)
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}

fun Context.shareFile(uri: Uri) {
    val file = File(uri.path)
    val uriForShare = getUriForFile(
        this,
        BuildConfig.APPLICATION_ID + ".fileprovider", file
    )
    val intent = Intent(Intent.ACTION_SEND)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.putExtra(Intent.EXTRA_STREAM, uriForShare)
    intent.type = "image/jpg"
    startActivity(Intent.createChooser(intent, "Share image via"))
}
