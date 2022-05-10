package com.nrsoft.starlightdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ToDoListClickedActivity : AppCompatActivity() {

    //전역변수
    //lateinit var date  : String
    val date : String? by lazy { intent.getStringExtra("date")?.substring(0,10) }

    val recyclerView : RecyclerView by lazy { findViewById(R.id.recycler_todolist) }

    //대량의 데이터들 리스트 참조변수
    var toDoListItems = mutableListOf<ToDoListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list_clicked)


        supportActionBar?.title = "Starlight Diary"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val date = intent.getStringExtra("date")?.substring(0,10)
//        Toast.makeText(this, "$date", Toast.LENGTH_SHORT).show()

        loadData()

    }

    fun loadData(){

        //1. Retrofit 객체 생성
        val builder : Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl("http://yn1016.dothome.co.kr")
        builder.addConverterFactory(ScalarsConverterFactory.create())
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit : Retrofit = builder.build()

        //인터페이스 객체 생성
        val retrofitService : RetrofitService = retrofit.create(RetrofitService::class.java)

        //추상메소드 실행 및 서버에 전달할 값 파라미터로 지정
        val call : Call<MutableList<ToDoListItem>> = retrofitService.getDataFromServer(date!!)

        //네트워크 작업 시작
        call.enqueue(object : Callback<MutableList<ToDoListItem>>{

            override fun onResponse(
                call: Call<MutableList<ToDoListItem>>,
                response: Response<MutableList<ToDoListItem>>
            ) {
                val items : MutableList<ToDoListItem>? = response.body()
                //Toast.makeText(this@ToDoListClickedActivity, "${items?.size}", Toast.LENGTH_SHORT).show()
                if (items!=null) {
                    toDoListItems.clear()
                    toDoListItems.addAll(items)

                    recyclerView.adapter = ToDoListAdapter(this@ToDoListClickedActivity, toDoListItems)
                }

            }

            override fun onFailure(call: Call<MutableList<ToDoListItem>>, t: Throwable) {
                Toast.makeText(this@ToDoListClickedActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}