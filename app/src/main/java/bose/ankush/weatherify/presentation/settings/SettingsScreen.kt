package bose.ankush.weatherify.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import bose.ankush.weatherify.R
import bose.ankush.weatherify.base.LocaleConfigMapper
import bose.ankush.weatherify.presentation.MainViewModel
import bose.ankush.weatherify.presentation.navigation.AppBottomBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    viewModel: MainViewModel,
    navController: NavController,
    onLanguageNavAction: (Array<String>) -> Unit,
    onNotificationNavAction: () -> Unit,
    onAvatarNavAction: () -> Unit,
) {
    val isNotificationBannerVisible = viewModel.showNotificationCardItem.collectAsState().value
    val tooltipState = remember { RichTooltipState() }
    val scope = rememberCoroutineScope()
    val languageList = LocaleConfigMapper.getAvailableLanguagesFromJson(
        jsonFile = "countryConfig.json",
        context = LocalContext.current
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ScreenHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 30.dp),
                onAvatarNavAction = onAvatarNavAction,
                scope = scope
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                // Notification block
                if (isNotificationBannerVisible) {
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 30.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Notification",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = "Turn on notification permission to get weather updates on the go.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Button(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .align(Alignment.End)
                                    .height(40.dp),
                                onClick = { onNotificationNavAction.invoke() })
                            { Text("Turn on") }
                        }
                    }
                }

                // Language block
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .clickable { onLanguageNavAction.invoke(languageList) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Language",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = "Select your preferred language for a personalized experience.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }

                // Get Premium block
                RichTooltipBox(
                    tooltipState = tooltipState,
                    title = { Text("Premium") },
                    text = { Text("Stay tuned! This feature is coming soon. Enable notifications to be the first to know.") },
                    action = {
                        Text(
                            text = "OK",
                            modifier = Modifier
                                .padding(top = 5.dp, bottom = 5.dp)
                                .clickable { scope.launch { tooltipState.dismiss() } }
                        )
                    }
                ) {
                    OutlinedCard(
                        modifier = Modifier
                            .tooltipAnchor()
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .clickable { scope.launch { tooltipState.show() } }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Get Premium",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                Text(
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = "Upgrade to Premium and unlock exclusive features, priority support, and an ad-free experience.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            AppBottomBar(
                isVisible = rememberSaveable { mutableStateOf(true) },
                navController = navController
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    onAvatarNavAction: () -> Unit,
    scope: CoroutineScope,
) {
    val tooltipState = remember { RichTooltipState() }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.settings_screen),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        RichTooltipBox(
            tooltipState = tooltipState,
            title = { Text("Hi Maa,") },
            text = { Text(text = "Baba sends you love, kisses and hug ❤\uFE0F") },
            action = {
                Text(
                    text = "Call him",
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .clickable {
                            scope.launch {
                                tooltipState.dismiss()
                                onAvatarNavAction.invoke()
                            }
                        }

                )
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.zobo),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .tooltipAnchor()
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { scope.launch { tooltipState.show() } }
            )
        }
    }
}