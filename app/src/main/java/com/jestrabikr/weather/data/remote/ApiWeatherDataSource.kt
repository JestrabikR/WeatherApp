package com.jestrabikr.weather.data.remote
import com.jestrabikr.weather.BuildConfig

class ApiWeatherDataSource(
    private val apiService: WeatherApiService,
) : WeatherDataSource {
    override suspend fun getWeather(city: String): WeatherDto {
        val apiKey = BuildConfig.OPENWEATHER_API_KEY

        val response = apiService.getWeather(city, apiKey)

        return WeatherDto(
            temperature = response.main.temp,
            description = response.weather.firstOrNull()?.description ?: "",
            icon = response.icon
        )
    }

}