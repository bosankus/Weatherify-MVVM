package bose.ankush.language.util

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

internal object LocaleHelper {

    fun String.getCountryFlag(): String {
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(this, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(this, 1) - asciiOffset + flagOffset
        val flag =
            (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
        return flag
    }

    fun String.getDisplayName(): String {
        val locale = Locale(this)
        return locale.getDisplayName(locale)
    }

    fun changeLanguageTo(languageCode: String): String {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        return AppCompatDelegate.getApplicationLocales().toLanguageTags()
    }
}