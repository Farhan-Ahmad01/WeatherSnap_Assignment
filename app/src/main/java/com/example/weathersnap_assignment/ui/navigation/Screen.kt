package com.example.weathersnap_assignment.ui.navigation

sealed class Screen(val route: String) {
    object Weather : Screen("weather")
    object CreateReport : Screen("create_report")
    object CustomCamera : Screen("custom_camera")
    object SavedReports : Screen("saved_reports")
}
