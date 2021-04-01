package com.example.coolieweather.presentation.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun Context.hasPermissions(permissions: Array<String>): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

