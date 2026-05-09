package com.example.weathersnap_assignment.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathersnap_assignment.data.models.GeocodingResult
import com.example.weathersnap_assignment.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.length > 2) {
                        fetchSuggestions(query)
                    } else {
                        _uiState.update { it.copy(citySuggestions = emptyList()) }
                    }
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query) }
    }

    private suspend fun fetchSuggestions(query: String) {
        _uiState.update { it.copy(isSuggestionsLoading = true) }
        val suggestions = repository.searchCity(query)
        _uiState.update { it.copy(citySuggestions = suggestions, isSuggestionsLoading = false) }
    }

    fun selectCity(city: GeocodingResult) {
        _uiState.update { it.copy(selectedCity = city, citySuggestions = emptyList(), searchQuery = city.name) }
        fetchWeather(city.latitude, city.longitude)
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getWeather(lat, lon)
                .onSuccess { weather ->
                    _uiState.update { it.copy(weatherData = weather, isLoading = false) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(error = exception.message ?: "Unknown error", isLoading = false) }
                }
        }
    }
}
