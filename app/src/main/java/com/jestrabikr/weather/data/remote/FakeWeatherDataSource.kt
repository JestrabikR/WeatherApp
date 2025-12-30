package com.jestrabikr.weather.data.remote

class FakeWeatherDataSource : WeatherDataSource {
    val fakeResponse = WeatherDto(
        name = "Brno",
        visibility = 10000,
        timezone = 3600,

        weather = WeatherDescriptionDto(
            main = "Clouds",
            description = "Overcast clouds",
            icon = "04d"
        ),

        main = MainDto(
            temperature = 22.5,
            feelsLike = 21.8,
            tempMin = 20.0,
            tempMax = 24.0,
            pressure = 1018,
            humidity = 65
        ),

        wind = WindDto(
            speed = 3.5,
        ),

        sys = SunDto(
            sunrise = 1766731424,
            sunset = 1766761086
        )
    )

    override suspend fun getWeather(city: String): WeatherDto {
        return fakeResponse
    }

    override suspend fun getWeather(lat: Double, lon: Double): WeatherDto {
        return fakeResponse
    }
}
