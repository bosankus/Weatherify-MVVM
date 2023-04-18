package bose.ankush.weatherify.presentation.air_quality

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import bose.ankush.weatherify.navigation.Screen
import bose.ankush.weatherify.presentation.ui.theme.TextWhite

@Composable
fun AirQualityDetailsScreen(
    lat: Double?,
    lon: Double?,
    navController: NavController,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "lat = $lat, lon = $lon",
                style = MaterialTheme.typography.h3,
                color = TextWhite,
                modifier = Modifier.clickable { navController.navigate(Screen.HomeScreen.withArgs("Adra")) }
            )
        }
    }
}