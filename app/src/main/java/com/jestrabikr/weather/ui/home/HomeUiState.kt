package com.jestrabikr.weather.ui.home

data class HomeUiState(
    val city: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val temperature: Double? = null,
    val description: String? = null
)