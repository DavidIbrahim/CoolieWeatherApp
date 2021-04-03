package com.example.coolieweather.presentation.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import timber.log.Timber
import java.util.*
import com.example.coolieweather.buisness.models.Result
import com.example.coolieweather.buisness.models.GeoPoint

private val permissions = arrayOf(
    android.Manifest.permission.ACCESS_FINE_LOCATION,
    android.Manifest.permission.ACCESS_COARSE_LOCATION
)


private var permissionLauncher: ActivityResultLauncher<String>? = null
/**
 *  should be called in onCreate Method
 */
fun Fragment.registerForLocationResult(onLocationResult: (Boolean) -> Unit) {
    Timber.d("registering for location permission")
    permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        onLocationResult(it)
    }
}

fun requestLocationPermission() {

    Timber.d("permission launcher is null ${permissionLauncher == null}")
    permissionLauncher?.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

}
fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}
fun Context.hasLocationPermissions(): Boolean {
    return hasPermissions(permissions)
}

fun  Context.getCityName(location: GeoPoint): Result<String> {
    return try {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        val cityName: String = addresses[0].adminArea

     Result.Success(cityName)
    } catch (e: Exception) {
        Result.Error(e)
    }
}