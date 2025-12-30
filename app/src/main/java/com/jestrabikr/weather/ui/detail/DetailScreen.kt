package com.jestrabikr.weather.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun DetailScreen(city: String, viewModel: DetailViewModel = viewModel<DetailViewModel>()) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(city) {
        val decodedCity = viewModel.decodeCity(city)
        viewModel.getData(decodedCity)
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.city,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))

                state.error != null -> {
                    Text(text = state.error!!, color = MaterialTheme.colorScheme.error)
                }

                state.temperature != null -> {
                    MainWeatherCard(state)

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            WeatherDetailItem(
                                label = "Feels Like",
                                value = "${state.feelsLike}°C",
                                iconUrl = "https://img.icons8.com/color/96/thermometer.png",
                                modifier = Modifier.weight(1f)
                            )
                            WeatherDetailItem(
                                label = "Daily Range",
                                value = "${state.tempMin}° / ${state.tempMax}°",
                                iconUrl = "https://img.icons8.com/color/96/thermometer-automation.png",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            WeatherDetailItem(
                                label = "Wind Speed",
                                value = "${state.windSpeed} m/s",
                                iconUrl = "https://img.icons8.com/color/96/wind.png",
                                modifier = Modifier.weight(1f)
                            )
                            WeatherDetailItem(
                                label = "Visibility",
                                value = "${(state.visibility ?: 0) / 1000} km",
                                iconUrl = "https://img.icons8.com/color/96/binoculars.png",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            WeatherDetailItem(
                                label = "Humidity",
                                value = "${state.humidity}%",
                                iconUrl = "https://img.icons8.com/color/96/humidity.png",
                                modifier = Modifier.weight(1f)
                            )
                            WeatherDetailItem(
                                label = "Pressure",
                                value = "${state.pressure} hPa",
                                iconUrl = "https://img.icons8.com/color/96/barometer.png",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            WeatherDetailItem(
                                label = "Sunrise",
                                value = formatTime(state.sunrise),
                                iconUrl = "https://img.icons8.com/color/96/sunrise.png",
                                modifier = Modifier.weight(1f)
                            )
                            WeatherDetailItem(
                                label = "Sunset",
                                value = formatTime(state.sunset),
                                iconUrl = "https://img.icons8.com/color/96/sunset.png",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainWeatherCard(state: DetailUiState) {
    val iconUrl = "https://openweathermap.org/img/wn/${state.weatherIcon}@4x.png"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .shadow(8.dp, CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = iconUrl,
                contentDescription = state.description,
                modifier = Modifier.size(120.dp)
            )
        }

        Text(
            text = "${state.temperature?.toInt() ?: "--"}°C",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = state.description?.replaceFirstChar { it.uppercase() } ?: "",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String, iconUrl: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.ElevatedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = iconUrl,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = value, style = MaterialTheme.typography.titleMedium)
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        }
    }
}

private fun formatTime(timestamp: Long?): String {
    if (timestamp == null) return "--:--"
    val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp * 1000))
}