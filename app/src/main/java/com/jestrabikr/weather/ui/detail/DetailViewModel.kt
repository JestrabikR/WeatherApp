package com.jestrabikr.weather.ui.detail

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
import java.net.URLDecoder
import kotlin.math.roundToInt

class DetailViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl(
        ApiWeatherDataSource(RetrofitInstance.api)
    )
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun decodeCity(city: String): String {
        val decoded = URLDecoder.decode(city, "UTF-8")

        _uiState.update {
            it.copy(
                city = decoded,
            )
        }

        return decoded
    }

    fun getData(city: String) {
        if (city.isBlank()) {
            _uiState.update { it.copy(error = "No city selected") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val result = repository.getWeather(city)

                _uiState.update {
                    it.copy(
                        isLoading = false,

                        description = result.weather.description.replaceFirstChar(Char::uppercase),
                        weatherIcon = result.weather.icon,

                        temperature = result.main.temperature.roundToInt(),
                        feelsLike = result.main.feelsLike.roundToInt(),
                        tempMin = result.main.tempMin.roundToInt(),
                        tempMax = result.main.tempMax.roundToInt(),

                        pressure = result.main.pressure,
                        humidity = result.main.humidity,
                        visibility = result.visibility,

                        windSpeed = result.wind.speed.roundToInt(),

                        sunrise = result.sys.sunrise + result.timezone,
                        sunset = result.sys.sunset + result.timezone
                    )
                }
            } catch (e: Exception) {
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