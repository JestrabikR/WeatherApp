package com.jestrabikr.weather.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel<HomeViewModel>()) {
    val state by viewModel.uiState.collectAsState()

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFfef7e8),
                        Color(0xFFe8e2cc)
                    )
                ))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = state.city,
                        onValueChange = viewModel::onCityChange,
                        label = { Text("City") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                    )

                    Button(
                        onClick = viewModel::onSearchClick,
                        enabled = !state.isLoading,
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(58.dp)
                            .width(54.dp)
                            .padding(top = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }


                HorizontalDivider()

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

                    else -> {
                        Text("Seach city to display temperature")
                    }
                }
            }
        }
    }
}