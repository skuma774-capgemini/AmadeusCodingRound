package com.example.amadeuscodinground.repositories

import com.example.amadeuscodinground.database.CityDao
import com.example.amadeuscodinground.database.RoomDataBaseSingleton
import com.example.amadeuscodinground.models.City
import com.example.amadeuscodinground.models.Main
import com.example.amadeuscodinground.models.Weather

class WeatherDataRepository(private val roomDataBaseSingleton: RoomDataBaseSingleton) {


    suspend fun deleteAllData(){
        roomDataBaseSingleton.getCityDataDao().deleteCity()
        roomDataBaseSingleton.getCityDataDao().deleteMain()
        roomDataBaseSingleton.getCityDataDao().deleteWeather()
    }
    suspend fun insertCity(city: City) = roomDataBaseSingleton.getCityDataDao().insertCityData(city)
    suspend fun insertMain(main: Main) = roomDataBaseSingleton.getCityDataDao().insertMainData(main)
    suspend fun insertWeather(weather: List<Weather>) = roomDataBaseSingleton.getCityDataDao().insertWeatherData(weather =weather )
    suspend fun getCityData(id:Int)= roomDataBaseSingleton.getCityDataDao().getCityDetails(id)
    suspend fun getAllData()= roomDataBaseSingleton.getCityDataDao().getAllDetails()

}