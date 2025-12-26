package com.jestrabikr.weather.data.remote
data class WeatherDto(
    val weather: WeatherDescriptionDto,
    val main: MainDto,
    val visibility: Int,
    val wind: WindDto,
    val clouds: CloudsDto,
    val sys: SunDto,
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
    val deg: Int,
    val gust: Double?
)

data class CloudsDto(
    val all: Int
)

data class SunDto(
    val sunrise: Long,
    val sunset: Long
)