package com.nrsoft.starlightdiary


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

public class RetrofitHelper {

    companion object{

        fun getRetrofitInstanceGson() : Retrofit {
            val builder : Retrofit.Builder = Retrofit.Builder()
            builder.baseUrl(baseUrl)
            builder.addConverterFactory(GsonConverterFactory.create())
            val retrofit = builder.build()

            return retrofit
        }

        fun getRetrofitInstanceScalars() : Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ScalarsConverterFactory.create()).build()
        }

        var baseUrl: String = "http://yn1016.dothome.co.kr"
    }

}