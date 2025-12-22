package com.jestrabikr.weather.data.repository

import com.jestrabikr.weather.data.remote.FakeWeatherDataSource
import com.jestrabikr.weather.data.remote.WeatherDto

class WeatherRepositoryImpl(
    private val dataSource: FakeWeatherDataSource
) : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherDto {
        return dataSource.getWeather(city)
    }
}