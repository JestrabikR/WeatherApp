package com.jestrabikr.weather.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onCityChange(value: String) {
        _uiState.update {
            it.copy(city = value, error = null)
        }
    }

    fun onSearchClick() {
        //TODO
    }
}