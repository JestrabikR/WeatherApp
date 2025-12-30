package com.jestrabikr.weather.ui.home

data class HomeUiState(
    val city: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val temperature: Int? = null,
    val description: String? = null,
    val weatherIcon: String? = null,
    val name: String = ""
)