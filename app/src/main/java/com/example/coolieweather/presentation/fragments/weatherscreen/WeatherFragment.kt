package com.example.coolieweather.presentation.fragments.weatherscreen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.coolieweather.R
import com.example.coolieweather.buisness.Result
import com.example.coolieweather.buisness.models.GeoPoint
import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.presentation.CoolieWeatherTheme
import com.example.coolieweather.presentation.utils.hasLocationPermissions
import com.example.coolieweather.presentation.utils.isLocationEnabled
import com.example.coolieweather.presentation.utils.registerForLocationResult
import com.example.coolieweather.presentation.utils.requestLocation
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
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
                checkLocationEnabled()
                createLocationRequest()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!requireContext().hasLocationPermissions())
            handleRequestingLocation()
    }

    private fun handleRequestingLocation() {
        if (requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //todo explain to the user why we need location permission then
        }
        Timber.d("requesting location")
        requestLocation()

    }

    private fun goToCameraFragment() {
        findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCameraFragment())
    }


    private fun checkLocationEnabled() {
        val locationEnabled = requireContext().isLocationEnabled()
        if (!locationEnabled) Toast.makeText(
            requireContext(),
            getString(R.string.please_enable_location_from_settings),
            Toast.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("MissingPermission")
    private fun createLocationRequest() {
        Timber.d("creating location request")

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            Timber.d("location is ss$location")
            viewModel.updateWeatherDetails(GeoPoint(location.latitude, location.longitude))


        }

    }

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

                    WeatherScreen(currentBackground = currentBackGround, weatherData)
                    { this@WeatherFragment.goToCameraFragment() }
                }
            }
        }
    }


}


