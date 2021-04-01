package com.example.coolieweather.presentation.fragments.camera

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coolieweather.R
import com.example.coolieweather.presentation.utils.createImageFile
import com.example.coolieweather.presentation.utils.hasCameraPermissions
import com.example.coolieweather.presentation.utils.registerForCameraResult
import com.example.coolieweather.presentation.utils.requestCamera
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraFragment : Fragment() {
    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    private val viewModel: CameraViewModel by viewModels()

    private lateinit var viewFinder: PreviewView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.camera_fragment, container, false)
        view.findViewById<Button>(R.id.camera_capture_button).setOnClickListener { takePhoto() }
        viewFinder = view.findViewById(R.id.viewFinder)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request camera permissions
        if (requireActivity().hasCameraPermissions()) {
            startCamera()
        } else {
            registerForCameraResult { granted: Boolean ->
                if (granted) startCamera()
                else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.camera_permission_not_granted),
                        Toast.LENGTH_SHORT
                    ).show()
                    lifecycleScope.launch {
                        delay(1000)
                        findNavController().navigateUp()

                    }
                }
            }
requestCamera()
        }


        // Set up the listener for take photo button


        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return
        // Create time-stamped output file to hold the image
        val photoFile = requireActivity().createImageFile()
        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.image_captured_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    Timber.d(msg)
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(viewFinder.display.rotation)
                .build()
            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Timber.e(exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }


}