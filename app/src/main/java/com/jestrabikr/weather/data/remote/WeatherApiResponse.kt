package com.jestrabikr.weather.data.remote

data class WeatherApiResponse(
    val main: Main,
    val weather: List<WeatherDescription>
)

data class Main(
    val temp: Double
)

data class WeatherDescription(
    val description: String
)