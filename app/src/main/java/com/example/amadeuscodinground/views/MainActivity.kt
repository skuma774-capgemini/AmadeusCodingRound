package com.example.amadeuscodinground.views


import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amadeuscodinground.R
import com.example.amadeuscodinground.database.RoomDataBaseSingleton
import com.example.amadeuscodinground.databinding.ActivityMainBinding
import com.example.amadeuscodinground.factories.MainViewModelFactory
import com.example.amadeuscodinground.models.WeatherData
import com.example.amadeuscodinground.repositories.WeatherDataRepository
import com.example.amadeuscodinground.view_models.MainViewModel
import com.example.amadeuscodinground.views.adapters.ItemLoadLis
import com.example.amadeuscodinground.views.adapters.WeatherDataAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    var repository: WeatherDataRepository? = null
    var startData = 10
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        repository =WeatherDataRepository(RoomDataBaseSingleton.getDatabase(this))
        repository?.let {
            mainViewModel = ViewModelProvider(
                this, MainViewModelFactory(
                    application,
                    repository!!
                )
            )[MainViewModel::class.java]
        }
        mainViewModel.weatherDataList.observe(this) {
            setAdapter(it)
        }
        setUpList()

    }
    private fun setUpList() {
        recyclerView = binding.rvWeather
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.setHasFixedSize(true)
    }

    private fun setAdapter(weather: List<WeatherData>) {
        if (recyclerView?.adapter == null)
            recyclerView?.adapter =
                WeatherDataAdapter(weather = weather as MutableList<WeatherData>,
                    object : ItemLoadLis {
                        override fun loadMore() {
                            mainViewModel.getMoreData(startData, startData + 10)
                            startData += startData
                        }
                    })
        else {
            (recyclerView?.adapter as WeatherDataAdapter).setDataList(weather as MutableList<WeatherData>)
        }
    }
}