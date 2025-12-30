package com.jestrabikr.weather.ui.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.net.URLEncoder

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel<HomeViewModel>()) {
    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold { padding ->
        Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
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
                val context = LocalContext.current
                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                        viewModel.getWeatherFromLocation(context)
                    }
                }

                OutlinedIconButton (
                    onClick = {
                        launcher.launch(arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ))
                        focusManager.clearFocus()
                    },
                    enabled = !state.isLoading,
                    shape = CircleShape,
                    modifier = Modifier
                        .height(58.dp)
                        .width(54.dp)
                        .padding(top = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Locate Me",
                    )
                }
                OutlinedTextField(
                    value = state.city,
                    onValueChange = viewModel::onCityChange,
                    label = { Text("City") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                )

                Button(
                    onClick = {
                        viewModel.onSearchClick()
                        focusManager.clearFocus()
                    },
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

                state.temperature != null && state.name.isNotEmpty() -> {
                    WeatherCard(state, navController)
                }

                else -> {
                    Text("Seach city to display temperature")
                }
            }
        }
    }
}

@Composable
fun WeatherCard(state: HomeUiState, navController: NavController) {
    val iconUrl = "https://openweathermap.org/img/wn/${state.weatherIcon}@2x.png"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = state.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "${state.temperature} °C",
                    style = MaterialTheme.typography.headlineLarge
                )

                if (!state.description.isNullOrEmpty()) {
                    Text(
                        text = state.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Button(
                    onClick = {
                        val encodedCity = URLEncoder.encode(state.name, "UTF-8")
                        navController.navigate("detail/$encodedCity")
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Detail")
                }
            }

            if (!state.weatherIcon.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .shadow(8.dp, RoundedCornerShape(50.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = state.description,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}