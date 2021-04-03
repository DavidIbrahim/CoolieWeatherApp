package com.example.coolieweather.presentation.fragments.weatherscreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coolieweather.R
import com.example.coolieweather.buisness.models.GeoPoint
import com.example.coolieweather.buisness.models.Result
import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.presentation.CoolieWeatherTheme
import com.example.coolieweather.presentation.utils.*
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hasLocationPermissions = requireContext().hasLocationPermissions()
        viewModel.grantedLocationPermission.value = hasLocationPermissions

        Timber.d("has location permission $hasLocationPermissions")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (!hasLocationPermissions)
            registerForLocationResult {
                Timber.d("location result = $it")
                viewModel.grantedLocationPermission.value = it
            }

        viewModel.grantedLocationPermission.observe(this) {
            if (it) {
                getLocation()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!requireContext().hasLocationPermissions()) {
            handleRequestingLocation()
        }
    }

    private fun handleRequestingLocation() {
        lifecycleScope.launch {
            //to allow user to read that we needs his location before requesting permission
            delay(3000)

            Timber.d("requesting location")
            requestLocationPermission()
        }


    }

    private fun goToCameraFragment() {
        findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCameraFragment())
    }




    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationEnabled = requireContext().isLocationEnabled()


        Timber.d("getting location")
        //if location is already enabled location request won't return results till
        // it's toggled off and on again on some devices so used last location in this case
        if (locationEnabled)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                Timber.d("location is $location")
                if (location != null)
                    viewModel.updateWeatherDetails(GeoPoint(location.latitude, location.longitude))
                else createLocationRequest()
            }
        else {
            createLocationRequest()
        }
    }
    @SuppressLint("MissingPermission")
    private fun createLocationRequest() {
        Timber.d("creating location request")

        val locationRequest = LocationRequest.create();
        locationRequest.interval = 1000;
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)

                Timber.d("location result called with $p0")

                if (p0 != null) {
                    val location: Location = p0.lastLocation
                    Timber.d("location is $location")
                    if(location !=null) {
                        viewModel.updateWeatherDetails(
                            GeoPoint(
                                location.latitude,
                                location.longitude
                            )
                        )
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }

            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    private fun gotoGalleryFragment() {
        findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToGalleryFragment())
    }


    fun shareImage() {
        viewModel.currentWeatherImageUri.let {
            if (it == null) Toast.makeText(
                requireContext(),
                getString(R.string.add_image_first_to_share),
                Toast.LENGTH_SHORT
            ).show()
            else
                requireContext().shareFile(it)

        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                CoolieWeatherTheme {
                    val currentBackGround by viewModel.currentBackgroundImageUri.observeAsState()
                    val weatherData: Result<WeatherData>? by viewModel.weatherData.observeAsState()
                    val currentLocation: GeoPoint? by viewModel.currentLocation.observeAsState()
                    val stillSearchingForLocation = currentLocation == null
                    WeatherScreen(
                        currentBackground = currentBackGround,
                        stillSearchingForLocation = stillSearchingForLocation,
                        weatherData = weatherData,
                        goToCamera = { this@WeatherFragment.goToCameraFragment() },
                        goToGallery = { this@WeatherFragment.gotoGalleryFragment() },
                        saveImageInDatabase = viewModel::saveWeatherImageInDatabase,
                        shareImage = { shareImage() },
                        reloadWeatherData = viewModel::fetchWeatherData
                    )
                }
            }
        }
    }


}


