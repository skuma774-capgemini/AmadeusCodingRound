package com.example.amadeuscodinground.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.amadeuscodinground.R
import com.example.amadeuscodinground.databinding.ItemLoadBinding
import com.example.amadeuscodinground.databinding.ItemTwoBinding
import com.example.amadeuscodinground.databinding.ItemZeroBinding
import com.example.amadeuscodinground.models.City
import com.example.amadeuscodinground.models.WeatherData
import java.util.*
import kotlin.collections.ArrayList

class WeatherDataAdapter(val weather: MutableList<WeatherData>, val lis: ItemLoadLis) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var cityFilterList: MutableList<WeatherData> = mutableListOf()

    init {
        cityFilterList = weather
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                ZeroViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_zero,
                        parent,
                        false
                    )
                )
            }
            2 -> {
                TwoViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_two,
                        parent,
                        false
                    )
                )
            }
            else -> {
                LoadHolder(
                    (DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_load,
                        parent,
                        false
                    ))
                )
            }
        }
    }

    fun setDataList(weather: MutableList<WeatherData>) {
        cityFilterList = weather
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val zeroViewHolder = holder as ZeroViewHolder
                zeroViewHolder.bind(cityFilterList[position])

            }
            2 -> {
                val twoViewHolder = holder as TwoViewHolder
                twoViewHolder.bind(cityFilterList[position])
            }
            3 -> {
                val twoViewHolder = holder as LoadHolder
                twoViewHolder.bind(lis)
            }
        }

    }

    override fun getItemCount(): Int {
        return cityFilterList.size.plus(1)
    }

    override fun getItemViewType(position: Int): Int {
        // return 0 or 2 depending on position
        if (position == weather.size) {
            return 3
        }
        return position % 2 * 2;
    }

    class ZeroViewHolder(private val zeroBinding: ItemZeroBinding) :
        RecyclerView.ViewHolder(zeroBinding.root) {
        fun bind(data: WeatherData) {
            zeroBinding.tvCountryWithCityName.text = "${data.city?.name},${data.city?.country}"
            zeroBinding.tvDetails.text = data.weather?.get(0)?.description
            zeroBinding.tvTemp.text = data.main?.temp.toString()
            zeroBinding.tvTempRang.text = "${data.main?.temp_min}to${data.main?.temp_max}"
        }

    }

    class TwoViewHolder(private val twoBinding: ItemTwoBinding) :
        RecyclerView.ViewHolder(twoBinding.root) {
        fun bind(data: WeatherData) {
            twoBinding.tvCountryWithCityName.text = "${data.city?.name},${data.city?.country}"
            twoBinding.tvDetails.text = data.weather?.get(0)?.description
            twoBinding.tvTemp.text = data.main?.temp.toString()
            twoBinding.tvTempRang.text = "${data.main?.temp_min}to${data.main?.temp_max}"

        }
    }

    class LoadHolder(private val loadBinding: ItemLoadBinding) :
        RecyclerView.ViewHolder(loadBinding.root) {
        fun bind(lis: ItemLoadLis) {
            loadBinding.loadMore.setOnClickListener {
                lis.loadMore()
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    cityFilterList = weather
                } else {
                    val resultList = ArrayList<WeatherData>()
                    for (row in weather) {

                        if (row.city?.findname?.lowercase(Locale.ROOT)
                                ?.contains(charSearch.lowercase(Locale.ROOT))!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    cityFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = cityFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                cityFilterList = results?.values as MutableList<WeatherData>
                notifyDataSetChanged()
            }
        }
    }
}

interface ItemLoadLis {
    fun loadMore()
}
