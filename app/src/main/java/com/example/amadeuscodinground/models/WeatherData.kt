package com.example.amadeuscodinground.models

import androidx.room.*


data class WeatherData(
    @Embedded var city: City?=City(),
    @Relation(
        parentColumn = "id",
        entityColumn = "weather_cityId"
    )
    var weather: List<Weather?>?= listOf(),
    @Relation(
        parentColumn = "id",
        entityColumn = "main_cityId"
    )
    var main: Main?= Main()

)

@Entity
data class City(
    @PrimaryKey
    var id: Long?=null,// 1283240
    var country: String?="", // NP
    var findname: String?="", // KATHMANDU
    var name: String?="", // Kathmandu
    var zoom: Double? =0.0// 7
)
@Entity
data class Main(
    @ColumnInfo(name = "main_cityId")
    var city_id: Long?=0,
    @PrimaryKey(autoGenerate = true)
    var mainId: Long?=null,
    var humidity: Double?=0.0, // 45
    var pressure: Double?=0.0, // 1017
    var temp: Double?=0.0, // 291.15
    var temp_max: Double?=0.0, // 291.15
    var temp_min: Double?=0.0 // 291.15
)
@Entity
data class Weather(

    @PrimaryKey(autoGenerate = true)
    var autoIdGenerated:Long? = null,
    var weather_id: Long?=null,// 501
    @ColumnInfo(name = "weather_cityId")
    var city_id: Long?=0,
    var description: String?="", // proximity moderate rain
    var icon: String?="", // 10d
    var main: String?="" // Rain
)



