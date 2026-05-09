package com.example.weathersnap_assignment.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathersnap_assignment.data.local.ReportDao
import com.example.weathersnap_assignment.data.local.ReportEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportUiState(
    val city: String = "",
    val temperature: Double = 0.0,
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val pressure: Double = 0.0,
    val notes: String = "",
    val imagePath: String? = null,
    val originalSize: Long = 0,
    val compressedSize: Long = 0,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportDao: ReportDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState = _uiState.asStateFlow()

    fun setWeatherData(city: String, temp: Double, hum: Int, wind: Double, press: Double) {
        _uiState.update {
            it.copy(city = city, temperature = temp, humidity = hum, windSpeed = wind, pressure = press)
        }
    }

    fun setImageData(path: String, originalSize: Long, compressedSize: Long) {
        _uiState.update {
            it.copy(imagePath = path, originalSize = originalSize, compressedSize = compressedSize)
        }
    }

    fun onNotesChange(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun saveReport() {
        val state = _uiState.value
        if (state.imagePath == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val entity = ReportEntity(
                city = state.city,
                temperature = state.temperature,
                humidity = state.humidity,
                windSpeed = state.windSpeed,
                pressure = state.pressure,
                notes = state.notes,
                imagePath = state.imagePath,
                originalSize = state.originalSize,
                compressedSize = state.compressedSize,
                timestamp = System.currentTimeMillis()
            )
            reportDao.insertReport(entity)
            _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
        }
    }
}
