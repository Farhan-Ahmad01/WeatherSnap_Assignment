package com.example.weathersnap_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weathersnap_assignment.ui.navigation.Screen
import com.example.weathersnap_assignment.ui.screens.*
import com.example.weathersnap_assignment.ui.theme.WeatherSnap_AssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherSnap_AssignmentTheme {
                WeatherSnapAppNavigation()
            }
        }
    }
}

@Composable
fun WeatherSnapAppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Weather.route,
        enterTransition = { fadeIn() + slideInHorizontally { it } },
        exitTransition = { fadeOut() + slideOutHorizontally { -it } },
        popEnterTransition = { fadeIn() + slideInHorizontally { -it } },
        popExitTransition = { fadeOut() + slideOutHorizontally { it } }
    ) {
        composable(Screen.Weather.route) {
            WeatherScreen(
                onCreateReport = { city, temp, hum, wind, press ->
                    navController.navigate(
                        "${Screen.CreateReport.route}/$city/$temp/$hum/$wind/$press"
                    )
                },
                onNavigateToReports = {
                    navController.navigate(Screen.SavedReports.route)
                }
            )
        }
        
        composable(
            route = "${Screen.CreateReport.route}/{city}/{temp}/{hum}/{wind}/{press}",
            arguments = listOf(
                navArgument("city") { type = NavType.StringType },
                navArgument("temp") { type = NavType.FloatType },
                navArgument("hum") { type = NavType.IntType },
                navArgument("wind") { type = NavType.FloatType },
                navArgument("press") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            val temp = backStackEntry.arguments?.getFloat("temp") ?: 0f
            val hum = backStackEntry.arguments?.getInt("hum") ?: 0
            val wind = backStackEntry.arguments?.getFloat("wind") ?: 0f
            val press = backStackEntry.arguments?.getFloat("press") ?: 0f
            
            val viewModel: ReportViewModel = hiltViewModel()
            
            LaunchedEffect(city) {
                viewModel.setWeatherData(city, temp.toDouble(), hum, wind.toDouble(), press.toDouble())
            }
            
            val captureResult by backStackEntry.savedStateHandle.getStateFlow<CaptureResult?>(
                "capture_result", null
            ).collectAsState()
            
            LaunchedEffect(captureResult) {
                captureResult?.let {
                    viewModel.setImageData(it.imagePath, it.originalSize, it.compressedSize)
                }
            }

            ReportScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onCapturePhoto = {
                    navController.navigate(Screen.CustomCamera.route)
                },
                onSaveSuccess = {
                    navController.navigate(Screen.SavedReports.route) {
                        popUpTo(Screen.Weather.route)
                    }
                }
            )
        }
        
        composable(Screen.CustomCamera.route) {
            CustomCameraScreen(
                onClose = { navController.popBackStack() },
                onCaptured = { result ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("capture_result", result)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.SavedReports.route) {
            SavedReportsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
