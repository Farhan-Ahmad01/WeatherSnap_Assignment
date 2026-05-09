package com.example.weathersnap_assignment.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import androidx.camera.core.ImageCapture
import kotlinx.parcelize.Parcelize
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@Parcelize
data class CaptureResult(
    val imagePath: String,
    val originalSize: Long,
    val compressedSize: Long
) : Parcelable

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {

    private val _captureResult = MutableStateFlow<CaptureResult?>(null)
    val captureResult = _captureResult.asStateFlow()

    fun captureImage(imageCapture: ImageCapture, context: Context) {
        imageCapture.takePicture(
            Dispatchers.Main.asExecutor(),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    processImage(image, context)
                }

                override fun onError(exception: ImageCaptureException) {
                    // Handle error
                }
            }
        )
    }

    private fun processImage(image: ImageProxy, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val originalSize = bytes.size.toLong()

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            
            // Compression
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
            val compressedBytes = outputStream.toByteArray()
            val compressedSize = compressedBytes.size.toLong()

            // Save to file
            val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { it.write(compressedBytes) }

            _captureResult.value = CaptureResult(
                imagePath = file.absolutePath,
                originalSize = originalSize,
                compressedSize = compressedSize
            )
            image.close()
        }
    }
}

// Extension to convert CoroutineDispatcher to Executor
fun kotlinx.coroutines.CoroutineDispatcher.asExecutor() = java.util.concurrent.Executor { command ->
    kotlinx.coroutines.GlobalScope.launch(this) { command.run() }
}
