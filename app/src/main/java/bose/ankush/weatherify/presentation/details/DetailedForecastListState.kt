package bose.ankush.weatherify.presentation.details

import bose.ankush.weatherify.common.UiText
import bose.ankush.weatherify.data.remote.dto.ForecastDto
import bose.ankush.weatherify.domain.model.AvgForecast

data class DetailedForecastListState(
    val isLoading: Boolean = false,
    val forecasts: List<ForecastDto.ForecastList> = emptyList(),
    val error: UiText? = null
)
