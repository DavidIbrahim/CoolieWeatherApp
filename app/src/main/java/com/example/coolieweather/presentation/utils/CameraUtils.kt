package com.example.coolieweather.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.coolieweather.R
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private var permissionLauncher: ActivityResultLauncher<String>? = null

private val permissions = arrayOf(
    android.Manifest.permission.CAMERA,
)

fun requestCamera() {

    Timber.d("permission launcher is null ${permissionLauncher == null}")
    permissionLauncher?.launch(android.Manifest.permission.CAMERA)

}
fun Fragment.registerForCameraResult(onCameraResult: (Boolean) -> Unit) {
    Timber.d("registering for location permission")
    permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        onCameraResult(it)
    }
}
fun Context.hasCameraPermissions(): Boolean {
    return hasPermissions(permissions)
}



@Throws(IOException::class)
 fun Activity.createImageFile(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}
