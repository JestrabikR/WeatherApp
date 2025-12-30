package com.jestrabikr.weather.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jestrabikr.weather.data.remote.ApiWeatherDataSource
import com.jestrabikr.weather.data.remote.RetrofitInstance
import com.jestrabikr.weather.data.remote.WeatherDto
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

        loadWeather {
            repository.getWeather(city)
        }
    }

    @SuppressLint("MissingPermission")
    fun getWeatherFromLocation(context: Context) {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager

        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            _uiState.update {
                it.copy(error = "Please turn on location services")
            }
            return
        }

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                loadWeather {
                    repository.getWeather(location.latitude, location.longitude)
                }
            } else {
                val request = LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    1_000L
                )
                    .setWaitForAccurateLocation(true)
                    .setMaxUpdates(1)
                    .build()

                val callback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        val loc = result.lastLocation

                        if (loc != null) {
                            loadWeather {
                                repository.getWeather(loc.latitude, loc.longitude)
                            }
                        } else {
                            _uiState.update {
                                it.copy(error = "Location not available")
                            }
                        }

                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    request,
                    callback,
                    Looper.getMainLooper()
                )
            }
        }
    }

    private fun loadWeather(loader: suspend () -> WeatherDto) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val result = loader()

                _uiState.update {
                    it.copy(
                        name = result.name,
                        isLoading = false,
                        temperature = result.main.temperature.roundToInt(),
                        description = result.weather.main,
                        weatherIcon = result.weather.icon
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