package bose.ankush.weatherify.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bose.ankush.weatherify.base.common.UiText
import bose.ankush.weatherify.base.dispatcher.DispatcherProvider
import bose.ankush.weatherify.data.preference.PreferenceManager
import bose.ankush.weatherify.domain.use_case.get_air_quality.GetAirQuality
import bose.ankush.weatherify.domain.use_case.get_weather_reports.GetWeatherReport
import bose.ankush.weatherify.domain.use_case.refresh_weather_reports.RefreshWeatherReport
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val refreshWeatherReport: RefreshWeatherReport,
    private val getWeatherReport: GetWeatherReport,
    private val getAirQuality: GetAirQuality,
    private val locationProviderClient: FusedLocationProviderClient,
    private val preferenceManager: PreferenceManager,
    dispatchers: DispatcherProvider
) : ViewModel() {

    var permissionDialogQueue = mutableStateListOf<String>()
        private set

    private val _uiState = MutableStateFlow(UIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _launchPhoneCallPermission = MutableStateFlow(false)
    val launchPhoneCallPermission = _launchPhoneCallPermission.asStateFlow()

    private val _launchNotificationPermission = MutableStateFlow(false)
    val launchNotificationPermission = _launchNotificationPermission.asStateFlow()

    private val _showNotificationCardItem = MutableStateFlow(false)
    val showNotificationCardItem = _showNotificationCardItem.asStateFlow()

    private val dataFetchExceptionHandler = CoroutineExceptionHandler { _, e ->
        _uiState.update { UIState(error = UiText.DynamicText(e.message.toString())) }
    } + dispatchers.io

    fun dismissDialog() {
        permissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    ) {
        if (!isGranted && !permissionDialogQueue.contains(permission)) {
            permissionDialogQueue.add(permission)
        } else {
            fetchAndSaveLocationCoordinates()
        }
    }

    fun updatePhoneCallPermission(launchState: Boolean) {
        _launchPhoneCallPermission.update { launchState }
    }

    fun updateNotificationPermission(launchState: Boolean) {
        _launchNotificationPermission.update { launchState }
    }

    fun updateShowNotificationBannerState(visible: Boolean) {
        _showNotificationCardItem.update { visible }
    }

    @SuppressLint("MissingPermission")
    fun fetchAndSaveLocationCoordinates() {
        locationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModelScope.launch(dataFetchExceptionHandler) {
                    val coordinates = Pair(first = location.latitude, second = location.longitude)
                    // storing location on shared preference
                    preferenceManager.saveLocationPreferences(coordinates)
                    // updating ui state that we have got coordinates
                    _uiState.update { UIState(userLocation = coordinates) }
                    // load initial data when coordinates received
                    performInitialDataLoading()
                }
            }
        }
            .addOnFailureListener { e -> throw RuntimeException(e.message.toString()) }
    }


    // initial data loading to get things ready for UI
    private fun performInitialDataLoading() {
        viewModelScope.launch(dataFetchExceptionHandler) {
            /**
             * Get saved weather report.
             * In case there is no data, data will be refreshed as per user's location,
             * and data will flow to compose UI
             */

            // considering we have current location coordinates, we are fetching that from storage preference
            val preferences = preferenceManager.getLocationPreferenceFlow().first()
            val latitude = preferences[PreferenceManager.USER_LAT_LOCATION]
            val longitude = preferences[PreferenceManager.USER_LON_LOCATION]
            if (latitude != null && longitude != null) {
                val location = Pair(latitude, longitude)

                // fetch and save weather report from remote to ROOM DB
                refreshWeatherReport(location)

                // zip both data streams and collect to populate on UI state data class.
                // Also update UI state about user's location coordinates
                getAirQuality(location.first, location.second)
                    .combine(getWeatherReport.invoke(location)) { air, weather ->
                        UIState(
                            isLoading = false,
                            userLocation = location,
                            weatherData = weather,
                            airQualityData = air,
                        )
                    }.collect { newState ->
                        _uiState.update { newState }
                    }
            } else {
                // in case we don't have coordinates, we don't have any requirement yet other than this :(
                _uiState.update { UIState(isLoading = false) }
                throw RuntimeException("No location coordinates")
            }
        }
    }
}
