package com.example.coolieweather.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun Context.writeBitmapToFile(bitmap: Bitmap): Uri {
    val file = createImageFile()
    val fOut = FileOutputStream(file)
    GlobalScope.launch(Dispatchers.IO) {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
    }
    return Uri.fromFile(file)

}

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

