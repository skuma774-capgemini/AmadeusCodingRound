package com.example.amadeuscodinground.database

import androidx.room.*
import com.example.amadeuscodinground.models.*

@Dao
interface CityDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityData(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainData(main: Main)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weather: List<Weather>)

    @Transaction
    @Query("SELECT * FROM City where id=:cityId")
    suspend fun getCityDetails(cityId:Int):List<WeatherData>

    @Query("DELETE FROM CITY")
    fun deleteCity()

    @Query("DELETE FROM Main")
    fun deleteMain()

    @Query("DELETE FROM Weather")
    fun deleteWeather()

    @Transaction
    @Query("SELECT * FROM City")
    suspend fun getAllDetails():List<WeatherData>

    @Transaction
    @Query("SELECT * FROM Weather ")
    suspend fun getWeatherDetails():List<Weather>

}





