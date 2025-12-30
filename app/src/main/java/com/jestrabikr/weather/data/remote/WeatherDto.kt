package com.jestrabikr.weather.data.remote
data class WeatherDto(
    val name: String,
    val weather: WeatherDescriptionDto,
    val main: MainDto,
    val visibility: Int,
    val wind: WindDto,
    val sys: SunDto,
    val timezone: Int,
)

data class WeatherDescriptionDto(
    val main: String,
    val description: String,
    val icon: String?
)

data class MainDto(
    val temperature: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
)

data class WindDto(
    val speed: Double,
)

data class SunDto(
    val sunrise: Long,
    val sunset: Long
)