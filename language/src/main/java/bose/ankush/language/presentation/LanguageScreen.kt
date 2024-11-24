package bose.ankush.language.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import bose.ankush.language.R
import bose.ankush.language.util.LocaleHelper.changeLanguageTo
import bose.ankush.language.util.LocaleHelper.getCountryFlag
import bose.ankush.language.util.LocaleHelper.getDisplayName

@Composable
fun LanguageScreen(
    languages: Array<String>,
    navAction: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = { ScreenHeader(navAction) },
            content = { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    ShowUI(languages = languages)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenHeader(navAction: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.lib_screen_header),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(id = R.string.lib_screen_header),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { navAction.invoke() }
                    .padding(all = 3.dp)
            )
        }
    )
}


@Composable
private fun ShowUI(languages: Array<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        items(languages.size) { position ->
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .padding(all = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            val localeListCompat = changeLanguageTo(languages[position])
                            Log.d("LanguageScreen", "LanguageChangeSetting: $localeListCompat")
                        }
                        .weight(1f),
                    text = languages[position].let { "${it.getCountryFlag()}  ${it.getDisplayName()}" },
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            emojiSupportMatch = EmojiSupportMatch.None
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                )
                Icon(
                    modifier = Modifier.alpha(0f), // TODO: Should be visible if item is selected
                    imageVector = Icons.Filled.Check,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "${languages[position]} ${Icons.Filled.Check.name}"
                )
            }
        }
    }
}
