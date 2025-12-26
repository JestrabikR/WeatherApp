package com.jestrabikr.weather.data.mappers

import com.jestrabikr.weather.data.remote.MainDto
import com.jestrabikr.weather.data.remote.SunDto
import com.jestrabikr.weather.data.remote.WeatherApiResponse
import com.jestrabikr.weather.data.remote.WeatherDescriptionDto
import com.jestrabikr.weather.data.remote.WeatherDto
import com.jestrabikr.weather.data.remote.WindDto

fun WeatherApiResponse.toDto(): WeatherDto =
    WeatherDto(
        weather = WeatherDescriptionDto(
            main = weather.firstOrNull()?.main ?: "",
            description =  weather.firstOrNull()?.description ?: "",
            icon =  weather.firstOrNull()?.icon
        ),
        main = MainDto(
            temperature = main.temp,
            feelsLike = main.feelsLike,
            tempMin = main.tempMin,
            tempMax = main.tempMax,
            pressure = main.pressure,
            humidity = main.humidity
        ),
        visibility = visibility,
        wind = WindDto(
            speed = wind.speed,
            gust = wind.gust
        ),
        sys = SunDto(
            sunrise = sys.sunrise,
            sunset = sys.sunset
        )
    )
