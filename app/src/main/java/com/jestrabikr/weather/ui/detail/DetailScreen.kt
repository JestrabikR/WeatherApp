package com.jestrabikr.weather.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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

    Scaffold { padding->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =state.city,
                style = MaterialTheme.typography.headlineLarge
            )

            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                state.error != null -> {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                state.temperature != null -> {
                    val iconUrl = "https://openweathermap.org/img/wn/${state.weatherIcon}@2x.png"

                    if (state.weatherIcon != null) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .shadow(16.dp, CircleShape)
                                .background(MaterialTheme.colorScheme.surface, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = iconUrl,
                                contentDescription = state.description,
                                modifier = Modifier.size(108.dp)
                            )
                        }
                    }

                    Text(
                        text = "${state.temperature} °C",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    if (state.description != null) {
                        Text(
                            text = "${state.description}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}