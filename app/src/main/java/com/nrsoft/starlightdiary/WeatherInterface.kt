package com.nrsoft.starlightdiary

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherInterface {

    @GET("getUltraSrtFcst?serviceKey="+BuildConfig.API_KEY)

    fun getWeather(
        @Query("numOfRows") num_of_rows: Int, //한 페이지 결과 수
        @Query("pageNo") page_no: Int,  //페이지 번호
        @Query("dataType") data_type: String, //응답 자료 형식
        @Query("base_data") base_date: String, //발표 일자
        @Query("base_time") base_time: String, //발표 시각
        @Query("nx") nx: String, //예보지점 x좌표
        @Query("ny") ny: String, //예보지점 y좌표
    ): Call<WEATHER>

}

data class WEATHER(val response : RESPONSE)
data class RESPONSE(val header: HEADER, val body: BODY)
data class HEADER(val resultCode : Int, val resultMsg : String)
data class BODY(val dataType: String, val items:ITEMS, val totalCount : Int)
data class ITEMS(val item : List<ITEM>)

//category - 자료 구분 코드 fcstDate : 예측날짜 , fcstTime : 예보시각, fcstValue : 예보 값
data class ITEM(val category : String, val fcstDate : String, val fcstTime:String, val fcstValue : String)

//retrofit을 사용하기 위한 빌더 생성
private val retrofit = Retrofit.Builder()
    .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object ApiObject{
    val retrofitService : WeatherInterface by lazy { retrofit.create(WeatherInterface::class.java) }
}