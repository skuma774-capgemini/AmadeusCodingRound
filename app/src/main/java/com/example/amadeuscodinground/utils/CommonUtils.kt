package com.example.amadeuscodinground.utils

import android.annotation.SuppressLint
import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

object CommonUtils {
    fun getJsonFromAssets(context: Context):String{
        val inputStream = context.assets.open("weather.json")
        val size:Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer,Charsets.UTF_8)
    }

    @SuppressLint("DefaultLocale")
    fun getDateFromMS(millis: Long): String? {
        return String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
    }
    fun getFromAssets(context: Context,startPage:Int,endPage:Int): String {
        val br = BufferedReader( InputStreamReader(context.assets.open("weather.json"), "UTF-8"))
        br.use { br1 ->
            var line = ""
            if (startPage==0)
                for (i in startPage..endPage) {
                    line += br1.readLine() +"\n"
                }
            else{
                for (i in 0..startPage) {
                    br1.readLine()
                }
                for (i in startPage..endPage) {
                    line += br1.readLine() +"\n"
                }
            }
            return line
        }
        return ""
    }
}