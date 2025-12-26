package com.jestrabikr.weather.data.remote
import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    val name: String,
    val timezone: Int,
    val weather: List<WeatherDescription>,
    val main: Main,
    val wind: Wind,
    val visibility: Int,
    val sys: Sys,
    val dt: Long
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val gust: Double?
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)