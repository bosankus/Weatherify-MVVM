package bose.ankush.weatherify.data.room.weather

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import bose.ankush.weatherify.domain.model.AirQuality

@Database(
    entities = [WeatherEntity::class, AirQuality::class],
    version = 2,
    autoMigrations = [AutoMigration (from = 1, to = 2)]
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}