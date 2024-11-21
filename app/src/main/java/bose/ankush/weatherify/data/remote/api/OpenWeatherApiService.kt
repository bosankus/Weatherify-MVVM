package bose.ankush.weatherify.data.remote.api

import bose.ankush.weatherify.BuildConfig
import bose.ankush.weatherify.data.remote.dto.AirQualityDto
import bose.ankush.weatherify.data.room.weather.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Query

/**Created by
Author: Ankush Bose
Date: 05,May,2021
 **/
interface OpenWeatherApiService {

    @GET("/data/2.5/air_pollution")
    suspend fun getCurrentAirQuality(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") AppId: String = BuildConfig.OPEN_WEATHER_API
    ): AirQualityDto

    @GET("/data/3.0/onecall")
    suspend fun getOneCallWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") AppId: String = BuildConfig.OPEN_WEATHER_API
    ): WeatherEntity
}
