package com.nrsoft.starlightdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    lateinit var weatherRecyclerView : RecyclerView

    private var base_date = "20210628" //발표 일자
    private var base_time = "0630" //발표 시각
    private var nx = "55" //예보지점 x좌표
    private var ny = "127" //예보지점 y좌표

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_weather)

        val tvDate = findViewById<TextView>(R.id.tvDate)
        weatherRecyclerView= findViewById<RecyclerView>(R.id.recycler_weather)
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)

        //리사이클러뷰 매니저 설정
        weatherRecyclerView.layoutManager = LinearLayoutManager(this)

        //오늘 날짜 텍스트 뷰 설정
        tvDate.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time)+"날씨"

        //nx, ny  지점의 날씨 가져와서 설정하기
        setWeather(nx, ny)

        //새로고침 버튼 누르면 날씨 정보 다시 가져옴
        btnRefresh.setOnClickListener {
            setWeather(nx, ny)
        }
    }

    //날씨 설정하기
    private fun setWeather(nx : String, ny :String){

        //현재 날짜 시간 정보 get
        val cal = Calendar.getInstance()
        base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time) // 현재 날짜
        val timeHour = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시간
        val timeMinute = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time) //현재 분

        //api 가져오기
        base_time = getBaseTime(timeHour, timeMinute)

        if (timeHour == "00" && base_time == "2330"){
            cal.add(Calendar.DATE,-1).toString()
            base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }

        //날씨 정보 가져오기
        val call = ApiObject.retrofitService.getWeather(4, 1,"JSON", base_date, base_time , nx, ny)

        //비동기적으로 실행하기
        call.enqueue(object  : retrofit2.Callback<WEATHER> {

            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {//성공시
                if (response.isSuccessful) {
                    //날씨 정보 가져오기
                    val it: List<ITEM> = response.body()!!.response.body.items.item

                    //현재 시각부터 1시간 뒤의 날씨 6개를 담을 배열 생성
                    val weatherArray = arrayOf(
                        WeatherData(),
                        WeatherData(),
                        WeatherData(),
                        WeatherData(),
                        WeatherData(),
                        WeatherData()
                    )

                    //배열 채우기
                    var index = 0
                    val totalCount = response.body()!!.response.body.totalCount - 1
                    for (i in 0..totalCount) {
                        index %= 6
                        when (it[i].category) {
                            "RN1" -> weatherArray[index].rainType = it[i].fcstValue
                            "REH" -> weatherArray[index].humidity = it[i].fcstValue
                            "SKY" -> weatherArray[index].sky = it[i].fcstValue
                            "T1H" -> weatherArray[index].temp = it[i].fcstValue
                            else -> continue
                        }
                        index++

                    }

                    //각 날짜 시간 배열 설정
                    for (i in 0..5) weatherArray[i].fcstTime = it[i].fcstTime

                    //리사이클러뷰에 데이터연결
                    weatherRecyclerView.adapter = WeatherAdapter(weatherArray)

                    //잘 됐는지 확인
                    Toast.makeText(
                        this@WeatherActivity,
                        it[0].fcstDate + "" + it[0].fcstTime + "의 날씨 정보입니다.",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WEATHER>, t: Throwable) {//실패시
                val tvError = findViewById<TextView>(R.id.tvError)
                tvError.text = "api fail : " +t.message.toString()+"\n 다시 시도해주세요"
                tvError.visibility= View.VISIBLE
                Log.d("api fail", t.message.toString())
            }
        })
    }

    //baseTime 설정
    private fun getBaseTime(h : String, m : String) : String {

        var result=""

        //45분전이면 1시간 전으로 baseTime 설정하기
        if(m.toInt()<45){
            if(h=="00") result = "2330"

            else{
                var resultHour = h.toInt() -1

                if (resultHour < 10) result = "0" + resultHour + "30"

                else result = resultHour.toString() +"30"
            }
        }
        else result = h + "30"
        return  result
    }
}