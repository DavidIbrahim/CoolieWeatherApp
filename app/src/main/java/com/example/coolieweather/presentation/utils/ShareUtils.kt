package com.example.coolieweather.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


fun Context.shareFile(uri: Uri) {

    val intent = Intent(Intent.ACTION_SEND)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.type = "image/jpg"
    startActivity(Intent.createChooser(intent, "Share image via"))
}