package com.example.amadeuscodinground.factories

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amadeuscodinground.repositories.WeatherDataRepository
import com.example.amadeuscodinground.view_models.MainViewModel

class MainViewModelFactory(private val context: Application,private val repository: WeatherDataRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context,repository) as T
    }
}