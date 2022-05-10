package com.nrsoft.starlightdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter (var items : Array<WeatherData>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_weather, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount() : Int = items.count()

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun setItem(item : WeatherData){
            val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
            val tvRainType = itemView.findViewById<TextView>(R.id.rainy_type)
            val tvHumidity = itemView.findViewById<TextView>(R.id.tv_humidity)
            val tvSky = itemView.findViewById<TextView>(R.id.tv_sky)
            val tvTemp = itemView.findViewById<TextView>(R.id.tv_temp)

            tvTime.text = item.fcstTime
            tvRainType.text = getRainType(item.rainType)
            tvHumidity.text = item.humidity
            tvSky.text = getSky(item.sky)
            tvTemp.text = item.temp + "°"

        }
    }

    fun getRainType(rainType : String) : String {
        return when(rainType){
            "0" -> "없음"
            "1" -> "비"
            "2" -> "비/눈"
            "3" -> "눈"
            else -> "오류"
        }
    }

    fun getSky(sky : String) : String {
        return  when(sky){
            "1" -> "맑음"
            "3" -> "구름 많음"
            "4" -> "흐림"
            else -> "오류"
        }
    }
}