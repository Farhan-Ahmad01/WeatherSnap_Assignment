Demo Video Drive Link - https://drive.google.com/file/d/1e_TeyLa6CZNJeNdND-MoOod7DwQMjAqc/view?usp=sharing

WeatherSnap

WeatherSnap is an Android application built as part of an Android Internship Assignment.
The app allows users to search live weather information for cities, capture weather evidence using a custom CameraX implementation, compress captured images, save reports locally using Room Database, and view previously saved reports.

The project focuses on practical Android development skills using modern Android architecture and Jetpack libraries.

Features
Weather Search
Search weather by city name
Live city autocomplete suggestions
Open Meteo Geocoding API integration
Weather API integration using Open Meteo Forecast API
Loading, success, empty, and error state handling
Cached city suggestions to reduce unnecessary API calls
Create Weather Report
Display selected weather snapshot
Add custom notes
Capture weather evidence image
Animated image preview after capture
Custom Camera
Built completely using CameraX
Live camera preview
Capture image into local storage
No external camera intent used
Image Compression
Compress captured image before saving
Display original image size
Display compressed image size
Saved Reports
Save reports locally using Room Database
Persist data across app restarts
View all previously saved reports
Display:
weather details
notes
captured image
original image size
compressed image size
timestamp
Tech Stack
Kotlin
Jetpack Compose
MVVM Architecture
ViewModel
StateFlow
Coroutines
Hilt
Navigation Compose
Retrofit
Gson Converter
OkHttp Logging Interceptor
Room Database
CameraX
Material 3
Architecture

The project follows MVVM architecture with reactive UI state management using StateFlow.

UI (Compose)
   ↓
ViewModel
   ↓
Repository
   ↓
Remote API / Local Database
API Used
Open Meteo APIs
Geocoding API

Used for city autocomplete suggestions.

https://geocoding-api.open-meteo.com/v1/search
Forecast API

Used to fetch live weather data.

https://api.open-meteo.com/v1/forecast
App Flow
Weather Screen
   ↓
Create Report Screen
   ↓
Custom Camera Screen
   ↓
Create Report Screen
   ↓
Saved Reports Screen
Screens
Weather Screen
Search city
View suggestions
Fetch weather
Navigate to Create Report
Create Report Screen
View weather snapshot
Add notes
Capture photo
Save report
Custom Camera Screen
Live camera preview
Capture image
Compress image
Return image to report screen
Saved Reports Screen
View all saved reports
Display report details and image metadata
UI & UX

The application includes:

Material 3 design
Smooth navigation transitions
Animated loading and state changes
Animated city suggestions
Animated image preview
Responsive layouts
Project Goals

This assignment focuses on:

Clean Android architecture
Modern Android development practices
API integration
State management
CameraX implementation
Local persistence
Production-like UX polish
Setup Instructions
Clone Repository
git clone <your-repo-url>
Open Project

Open the project in Android Studio.

Run App
Sync Gradle
Run on physical device or emulator
Camera functionality works best on physical devices
Important Notes
No mock weather data is used
Weather data is fetched live from Open Meteo APIs
Reports are stored locally using Room Database
Custom camera implemented using CameraX
Device camera intent is not used
Deliverables Included
Complete Android source code
MVVM architecture implementation
Room database integration
CameraX implementation
Image compression functionality
Screen recording of app flow
