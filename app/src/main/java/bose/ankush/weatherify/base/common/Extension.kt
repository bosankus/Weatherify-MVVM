package bose.ankush.weatherify.base.common

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

/**Created by
Author: Ankush Bose
Date: 06,May,2021
 **/

object Extension {

    fun Double.toCelsius(): String = (this - 273).roundToInt().toString()

    fun Context.openAppSystemSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }

    fun Context.hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    /*fun List<ForecastDto.ForecastList>.getForecastListForNext4Days():
            List<AvgForecast> {
        return filter { list -> (list.dt?.isNotMatchingWithTodayAndWithinNext4Days() == true) }
            .parseEachDayFromList()
    }

    private fun List<ForecastDto.ForecastList>.parseEachDayFromList(): List<AvgForecast> {
        val listOfAvgForecast = ArrayList<AvgForecast>()
        var avgTemp: Int
        var dayName: String
        var feelsLike: String?
        for (i in 1..4 step 1) {
            var totalTemp = 0
            var counter = 0
            for (j in this.indices step 1) {
                val date = this[j].dt
                if (date?.let { DateTimeUtils.getDayWiseDifferenceFromToday(it) } == i) {
                    val forecastObj = this[j]
                    feelsLike = forecastObj.main?.feelsLike?.toCelsius()
                    totalTemp += forecastObj.main?.temp?.toCelsius()?.toInt()!!
                    counter++
                    if ((counter % 7) == 0) {
                        avgTemp = totalTemp / counter
                        dayName = DateTimeUtils.getDayNameFromEpoch(date)
                        val avgForecast =
                            AvgForecast(this.hashCode(), date, dayName, "$avgTemp", feelsLike)
                        listOfAvgForecast.add(avgForecast)
                    }
                }
            }
        }
        return listOfAvgForecast
    }

    private fun Int.isNotMatchingWithTodayAndWithinNext4Days(): Boolean {
        val givenDate = Date(this.toLong() * 1000)
        val givenDateCalender = Calendar.getInstance()
        givenDateCalender.time = givenDate
        val givenYear = givenDateCalender.get(Calendar.YEAR)
        val currentYear = DateTimeUtils.getTodayDateInCalenderFormat().get(Calendar.YEAR)
        val givenDateNumber = givenDateCalender.get(Calendar.DAY_OF_MONTH + 1)
        val todayDateNumber =
            DateTimeUtils.getTodayDateInCalenderFormat().get(Calendar.DAY_OF_MONTH + 1)
        val differenceOfDate = DateTimeUtils.getDayWiseDifferenceFromToday(this)
        return (givenDateNumber > todayDateNumber && givenYear == currentYear && (differenceOfDate <= 4))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun Context.openAppLocaleSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APP_LOCALE_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }

    fun isDeviceSDKAndroid13OrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }*/
}
