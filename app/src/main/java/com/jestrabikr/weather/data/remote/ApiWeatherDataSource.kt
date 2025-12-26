package com.jestrabikr.weather.data.remote
import com.jestrabikr.weather.BuildConfig
import com.jestrabikr.weather.data.mappers.toDto

class ApiWeatherDataSource(
    private val apiService: WeatherApiService,
) : WeatherDataSource {
    override suspend fun getWeather(city: String): WeatherDto {
        val apiKey = BuildConfig.OPENWEATHER_API_KEY

        val response = apiService.getWeather(city, apiKey)

        return response.toDto()
    }

}