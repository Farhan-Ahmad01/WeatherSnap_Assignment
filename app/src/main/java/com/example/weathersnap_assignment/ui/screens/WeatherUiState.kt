package com.example.weathersnap_assignment.ui.screens

import com.example.weathersnap_assignment.data.models.GeocodingResult
import com.example.weathersnap_assignment.data.models.WeatherResponse

data class WeatherUiState(
    val searchQuery: String = "",
    val citySuggestions: List<GeocodingResult> = emptyList(),
    val selectedCity: GeocodingResult? = null,
    val weatherData: WeatherResponse? = null,
    val isLoading: Boolean = false,
    val isSuggestionsLoading: Boolean = false,
    val error: String? = null
)
