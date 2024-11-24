package bose.ankush.weatherify.presentation.home

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import bose.ankush.weatherify.R
import bose.ankush.weatherify.base.common.UiText
import bose.ankush.weatherify.data.room.weather.WeatherEntity
import bose.ankush.weatherify.domain.model.AirQuality
import bose.ankush.weatherify.presentation.MainViewModel
import bose.ankush.weatherify.presentation.UIState
import bose.ankush.weatherify.presentation.home.component.BriefAirQualityReportCardLayout
import bose.ankush.weatherify.presentation.home.component.CurrentWeatherReportLayout
import bose.ankush.weatherify.presentation.home.component.DailyWeatherForecastReportLayout
import bose.ankush.weatherify.presentation.home.component.HourlyWeatherForecastReportLayout
import bose.ankush.weatherify.presentation.home.state.ShowError
import bose.ankush.weatherify.presentation.home.state.ShowLoading
import bose.ankush.weatherify.presentation.navigation.AppBottomBar
import bose.ankush.weatherify.presentation.navigation.Screen
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val context: Context = LocalContext.current
    val uiState: UIState = viewModel.uiState.collectAsState().value

    // reacting as per response state change
    Timber.tag("Ankush UI State").d("HomeScreen: %s", uiState)
    when {
        uiState.isLoading ->
            // Screen loading handler
            HandleScreenLoading()

        !uiState.error?.asString(context).isNullOrEmpty() ->
            // Screen error handler
            HandleScreenError(
                context,
                uiState.error,
                uiState.error
            ) { viewModel.fetchAndSaveLocationCoordinates() }

        uiState.weatherData?.current?.weather?.isNotEmpty() == true ||
                uiState.airQualityData?.aqi != null ->
            // Show data on UI
            ShowUIContainer(
                uiState.weatherData,
                uiState.airQualityData,
                navController
            )

        else ->
            // Screen loading handler for any other states
            HandleScreenLoading()
    }

    // Handle back button press to exit app
    BackHandler {
        (context as? Activity)?.finish()
    }
}

@Composable
fun HandleScreenLoading() {
    ShowLoading(modifier = Modifier.fillMaxSize())
}

@Composable
fun HandleScreenError(
    context: Context,
    weatherReports: UiText?,
    airQualityReports: UiText?,
    onErrorAction: () -> Unit
) {
    ShowError(
        modifier = Modifier.fillMaxSize().padding(all = 16.dp),
        msg =
        weatherReports?.asString(context) ?: airQualityReports?.asString(context),
        buttonText = stringResource(id = R.string.retry_btn_txt),
        buttonAction = onErrorAction
    )
}

@Composable
private fun ShowUIContainer(
    weatherReports: WeatherEntity?,
    airQualityReports: AirQuality?,
    navController: NavController
) {
    Box {
        Scaffold(content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding, verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // TODO: Show custom top app bar with future decided icons

                // Show current weather report
                item {
                    weatherReports?.current?.let { CurrentWeatherReportLayout(it) }
                        ?: Timber.d("Weather report is empty")
                }

                // Show brief air quality report
                item {
                    airQualityReports?.let {
                        BriefAirQualityReportCardLayout(airQualityReports) {
                            navController.navigate(Screen.AirQualityDetailsScreen.route)
                        }
                    }
                }

                // Show hourly weather forecast report
                item {
                    weatherReports?.hourly?.let { HourlyWeatherForecastReportLayout(it) }
                }

                // Show next 8 day's weather forecast report
                item {
                    Text(
                        text = stringResource(id = R.string.daily_forecast_heading_txt),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                }
                weatherReports?.daily?.let { list ->
                    items(list.size) { DailyWeatherForecastReportLayout(list, it) }
                }
            }
        }, bottomBar = {
            AppBottomBar(
                isVisible = rememberSaveable { mutableStateOf(true) },
                navController = navController
            )
        })
    }
}
