package com.jestrabikr.weather.data.repository

import com.jestrabikr.weather.data.remote.WeatherDataSource
import com.jestrabikr.weather.data.remote.WeatherDto

class WeatherRepositoryImpl(
    private val dataSource: WeatherDataSource
) : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherDto {
        return dataSource.getWeather(city)
    }

    override suspend fun getWeather(lat: Double, lon: Double): WeatherDto {
        return dataSource.getWeather(lat, lon)
    }
}