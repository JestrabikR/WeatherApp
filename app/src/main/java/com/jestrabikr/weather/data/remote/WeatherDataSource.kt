package com.jestrabikr.weather.data.remote

interface WeatherDataSource {
    suspend fun getWeather(city: String): WeatherDto
    suspend fun getWeather(lat: Double, lon: Double): WeatherDto
}