package com.jestrabikr.weather.data.remote

class FakeWeatherDataSource : WeatherDataSource {
    override suspend fun getWeather(city: String): WeatherDto {
        return WeatherDto(
            temperature = 22.5,
            description = "Cloudy",
            icon = "02d"
        )
    }
}