package com.example.weathersnap_assignment.data.repository

import com.example.weathersnap_assignment.data.models.GeocodingResult
import com.example.weathersnap_assignment.data.models.WeatherResponse
import com.example.weathersnap_assignment.data.remote.GeocodingApi
import com.example.weathersnap_assignment.data.remote.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val geocodingApi: GeocodingApi,
    private val weatherApi: WeatherApi
) {
    private val geocodingCache = mutableMapOf<String, List<GeocodingResult>>()

    suspend fun searchCity(query: String): List<GeocodingResult> {
        if (query.length <= 2) return emptyList()
        
        geocodingCache[query]?.let { return it }

        return try {
            val response = geocodingApi.searchCity(query)
            val results = response.results ?: emptyList()
            geocodingCache[query] = results
            results
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getWeather(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            val response = weatherApi.getForecast(lat, lon)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
