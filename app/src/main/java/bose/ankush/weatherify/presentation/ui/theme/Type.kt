package bose.ankush.weatherify.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bose.ankush.weatherify.R

val Glaind = FontFamily(
    Font(R.font.glaind_reg),
    Font(R.font.glaind_bold, FontWeight.Bold)
)

val GlaindTypography = Typography(
    h1 = TextStyle(
        fontFamily = Glaind,
        fontWeight = FontWeight.Thin,
        fontSize = 60.sp,
    ),
    h2 = TextStyle(
        fontFamily = Glaind,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = Glaind,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = Glaind,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = Glaind,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Glaind,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

val SFCompactDisplay = FontFamily(
    Font(R.font.sf_compact_display_medium),
    Font(R.font.sf_compact_display_thin)
)

val SFCompactDisplayTypography = Typography(
    h1 = TextStyle(
        fontFamily = SFCompactDisplay,
        fontWeight = FontWeight.Thin,
        fontSize = 60.sp,
    ),
    h2 = TextStyle(
        fontFamily = SFCompactDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = SFCompactDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = SFCompactDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = SFCompactDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = SFCompactDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)