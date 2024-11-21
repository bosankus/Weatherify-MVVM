package bose.ankush.weatherify.presentation

import bose.ankush.weatherify.base.common.UiText
import bose.ankush.weatherify.data.room.weather.WeatherEntity
import bose.ankush.weatherify.domain.model.AirQuality

data class UIState(
    val isLoading: Boolean = false,
    val userLocation: Pair<Double, Double>? = null,
    val weatherData: WeatherEntity? = null,
    val airQualityData: AirQuality? = null,
    val error: UiText? = null
)