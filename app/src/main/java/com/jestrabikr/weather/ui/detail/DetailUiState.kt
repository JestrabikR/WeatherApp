package com.jestrabikr.weather.ui.detail

data class DetailUiState(
    val city: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,

    val main: String? = null,
    val description: String? = null,
    val weatherIcon: String? = null,

    val temperature: Int? = null,
    val feelsLike: Int? = null,
    val tempMin: Int? = null,
    val tempMax: Int? = null,

    val pressure: Int? = null,
    val humidity: Int? = null,

    val windSpeed: Int? = null,

    val visibility: Int? = null,

    val sunrise: Long? = null,
    val sunset: Long? = null
)