package com.example.amadeuscodinground.view_models

import android.app.Application
import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.amadeuscodinground.models.City
import com.example.amadeuscodinground.models.Main
import com.example.amadeuscodinground.models.Weather
import com.example.amadeuscodinground.models.WeatherData
import com.example.amadeuscodinground.repositories.WeatherDataRepository
import com.example.amadeuscodinground.utils.CommonUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject


class MainViewModel(private val context: Application, private val repository: WeatherDataRepository) :
    AndroidViewModel(context) {

    private val weatherDataListObjects: MutableLiveData<List<WeatherData>> = MutableLiveData()

    val weatherDataList: LiveData<List<WeatherData>>
        get() = weatherDataListObjects

    init {
        viewModelScope.launch {
            val deleteJob = viewModelScope.async(Dispatchers.IO) {
                repository.deleteAllData()
            }
            deleteJob.await()
            setDataToList(0,10)
        }
    }

    private fun setDataToList(startPage:Int,endPage:Int){
        val jsonString = CommonUtils.getFromAssets(context,startPage,endPage)
        try {
            viewModelScope.launch {
                val job = viewModelScope.async {
                    val list = jsonString?.split("\r?\n|\r".toRegex())?.toTypedArray()
                    for (item in 0 until (list?.size ?: 0)) {
                        if (TextUtils.isEmpty(list?.get(item))) continue
                        val json = JSONObject(list?.get(item))
                        val cityObj = json.optJSONObject("city")
                        val cityData = City()
                        cityData.id = cityObj.optLong("id")
                        cityData.name = cityObj.optString("name")
                        cityData.findname = cityObj.optString("findname")
                        cityData.country = cityObj.optString("country")
                        cityData.zoom = cityObj.optDouble("zoom")
                        repository.insertCity(cityData)
                        val mainData = json.optJSONObject("main")
                        val main = Main()
                        main.city_id = cityObj.optLong("id")
                        main.temp = mainData.optDouble("temp")
                        main.pressure = mainData.optDouble("pressure")
                        main.humidity = mainData.optDouble("humidity")
                        main.temp_min = mainData.optDouble("temp_min")
                        main.temp_max = mainData.optDouble("temp_max")
                        repository.insertMain(main)
                        val weatherData = json.optJSONArray("weather")
                        val list = mutableListOf<Weather>()
                        for (i in 0 until weatherData.length()){
                            val weatherObj = weatherData.optJSONObject(i)
                            val weather = Weather()
                            weather.weather_id = weatherObj.optLong("id")
                            weather.icon = weatherObj.optString("icon")
                            weather.main = weatherObj.optString("main")
                            weather.description = weatherObj.optString("description")
                            weather.city_id = cityObj.optLong("id")
                            list.add(weather)
                        }
                        repository.insertWeather(list)
                    }

                }
                job.await()
                weatherDataListObjects.value = getData()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public fun getMoreData(startPage: Int,endPage: Int){
        setDataToList(startPage,endPage)
    }

    private suspend fun getData(): List<WeatherData> {
        val job = viewModelScope.async { repository.getAllData() }
        return job.await()
    }

    suspend fun getCityDetails(id: Int): List<WeatherData> {
        val job = viewModelScope.async { repository.getCityData(id) }
        return job.await()
    }
}

