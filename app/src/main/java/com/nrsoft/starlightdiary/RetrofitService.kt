package com.nrsoft.starlightdiary

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

public interface RetrofitService {

    @Multipart
    @POST("/DiaryRetrofit/saveData.php")
    fun postDataToServer (@PartMap dataPart : Map<String, String>,
                          @Part filePart : MultipartBody.Part?):Call<String>

    @GET("/DiaryRetrofit/loadData.php")
    fun getDataToServer(@Query("date") date : String ) : Call<String>

    @GET("/DiaryRetrofit/loadData.php")
    fun getDataFromServer(@Query("date") date : String ) : Call<MutableList<ToDoListItem>>

}