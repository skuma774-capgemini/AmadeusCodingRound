package com.example.amadeuscodinground.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.amadeuscodinground.models.*


@Database(entities = [City::class,Main::class,Weather::class],version = 1,exportSchema = false)
abstract class RoomDataBaseSingleton: RoomDatabase() {
    abstract fun getCityDataDao():CityDao

    companion object{
        private const val DB_NAME = "weather_database.db"
        @Volatile
        private var INSTANCE:RoomDataBaseSingleton?=null
        fun getDatabase(context: Context): RoomDataBaseSingleton {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBaseSingleton::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

}