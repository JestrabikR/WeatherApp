package com.jestrabikr.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jestrabikr.weather.data.remote.ApiWeatherDataSource
import com.jestrabikr.weather.data.remote.RetrofitInstance
import com.jestrabikr.weather.data.repository.WeatherRepository
import com.jestrabikr.weather.data.repository.WeatherRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HomeViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl(
        ApiWeatherDataSource(RetrofitInstance.api)
    )
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onCityChange(value: String) {
        _uiState.update {
            it.copy(city = value, error = null)
        }
    }

    fun onSearchClick() {
        val city = _uiState.value.city

        if (city.isBlank()) {
            _uiState.update { it.copy(error = "Enter city name") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val result = repository.getWeather(city)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        temperature = result.main.temperature.roundToInt().toDouble(),
                        description = result.weather.main,
                        weatherIcon = result.weather.icon
                    )
                }
            } catch (e: Exception) {
                //TODO: load from cache (db)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Could not load weather"
                    )
                }
            }
        }
    }
}