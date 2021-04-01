package com.example.coolieweather.presentation.fragments.weatherscreen

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coolieweather.presentation.CoolieWeatherTheme
import com.example.coolieweather.presentation.utils.hasLocationPermissions
import com.example.coolieweather.presentation.utils.isLocationEnabled
import com.example.coolieweather.presentation.utils.registerForLocationResult
import com.example.coolieweather.presentation.utils.requestLocationAndCamera
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
@AndroidEntryPoint
class WeatherFragment : Fragment() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hasLocationPermissions = requireContext().hasLocationPermissions()
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
            Toast.makeText(
                requireContext(),
                "Accessing your location will help us show your city and local time to your partners",
                Toast.LENGTH_LONG
            ).show()
        }
        Timber.d("requesting location")
        requestLocationAndCamera()

    }
    private fun goToCameraFragment(){
        findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCameraFragment())
    }


    private fun checkLocationEnabled() {
        val locationEnabled = requireContext().isLocationEnabled()
        if (!locationEnabled) Toast.makeText(
            requireContext(),
            "Please Enable location from settings",
            Toast.LENGTH_SHORT
        ).show()
    }
    @SuppressLint("MissingPermission")
    private fun createLocationRequest() {

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
                    viewModel.setUserLocation(location)
                    fusedLocationClient.removeLocationUpdates(this)
                }

            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                CoolieWeatherTheme {
                    WeatherScreen { this@WeatherFragment.goToCameraFragment() }
                }
            }
        }
    }


}


