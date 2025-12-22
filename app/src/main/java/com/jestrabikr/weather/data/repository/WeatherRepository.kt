package com.jestrabikr.weather.data.repository

import com.jestrabikr.weather.data.remote.WeatherDto

interface WeatherRepository {
    suspend fun getWeather(city: String): WeatherDto
}