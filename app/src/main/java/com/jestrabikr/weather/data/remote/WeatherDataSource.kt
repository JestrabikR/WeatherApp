package com.jestrabikr.weather.data.remote

interface WeatherDataSource {
    suspend fun getWeather(city: String): WeatherDto
}