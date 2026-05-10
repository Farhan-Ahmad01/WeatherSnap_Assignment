Demo Video Drive Link - https://drive.google.com/file/d/1e_TeyLa6CZNJeNdND-MoOod7DwQMjAqc/view?usp=sharing

# WeatherSnap

WeatherSnap is an Android application built as part of an Android Internship Assignment.

The app allows users to search live weather information for cities, capture weather evidence using a custom CameraX implementation, compress captured images, save reports locally using Room Database, and view previously saved reports.

The project focuses on practical Android development skills using modern Android architecture and Jetpack libraries.

---

# Features

## Weather Search
- Search weather by city name
- Live city autocomplete suggestions
- Open Meteo Geocoding API integration
- Weather API integration using Open Meteo Forecast API
- Loading, success, empty, and error state handling
- Cached city suggestions to reduce unnecessary API calls

## Create Weather Report
- Display selected weather snapshot
- Add custom notes
- Capture weather evidence image
- Animated image preview after capture

## Custom Camera
- Built completely using CameraX
- Live camera preview
- Capture image into local storage
- No external camera intent used

## Image Compression
- Compress captured image before saving
- Display original image size
- Display compressed image size

## Saved Reports
- Save reports locally using Room Database
- Persist data across app restarts
- View all previously saved reports

Each report contains:
- Weather details
- Notes
- Captured image
- Original image size
- Compressed image size
- Timestamp

---

# Tech Stack

- Kotlin
- Jetpack Compose
- MVVM Architecture
- ViewModel
- StateFlow
- Coroutines
- Hilt
- Navigation Compose
- Retrofit
- Gson Converter
- OkHttp Logging Interceptor
- Room Database
- CameraX
- Material 3

