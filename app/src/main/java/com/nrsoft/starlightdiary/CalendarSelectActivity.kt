package com.nrsoft.starlightdiary

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CalendarSelectActivity : AppCompatActivity() {

    //date 전역 변수
    lateinit var date : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_select)


        supportActionBar?.title = "Diary Schedule"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val year = intent.getIntExtra("year", 0)
        val month = intent.getIntExtra("month", 0)
        val day = intent.getIntExtra("day", 0)

        //오늘 날짜 불러오기
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day)
        date= SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

        Toast.makeText(this, "$date", Toast.LENGTH_SHORT).show()

        //Toast.makeText(this, "$year $month $day", Toast.LENGTH_SHORT).show()
        var tvYear = findViewById<TextView>(R.id.tv_year)
        tvYear.setText("$year"+"년")

        var tvMonth = findViewById<TextView>(R.id.tv_month)
        tvMonth.setText("${month+1}"+"월")

        var tvDay = findViewById<TextView>(R.id.tv_day)
        tvDay.setText("$day"+"일")

        val btn= findViewById<Button>(R.id.btn_save)

        //저장 버튼 클릭시
        btn.setOnClickListener { clickSave()}

        //이미지 버튼 클릭시
        val btn_image = findViewById<Button>(R.id.btn_image)

        btn_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            resultLauncher.launch(intent)

        }

    }

    val resultLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object : ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            if(result?.resultCode == RESULT_OK){
                val imageUri= result?.data?.data
                //Toast.makeText(this@CalendarSelectActivity, "${imageUri.toString()}", Toast.LENGTH_SHORT).show()
                Glide.with(this@CalendarSelectActivity).load(imageUri).into(findViewById(R.id.iv))

                imgPath= getRealPathFromUri(imageUri)

                //확인
//                val builder = AlertDialog.Builder(this@CalendarSelectActivity)
//                builder.setMessage("$imgPath").create().show()

            }
        }

    })

    //이미지 파일의 절대 경로
    var imgPath : String?=null


    //Uri = 절대 경로로 바꿔서 리턴시켜주는 메소드
    fun getRealPathFromUri(imageUri: Uri?): String {
        val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
        val loader: CursorLoader = CursorLoader(this, imageUri!!, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_index : Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result : String = cursor.getString(column_index)
        cursor.close()
        return result
    }

    fun clickSave(){

        //작성한 데이터들 서버에 업로드 하기

        //전송할 데이터들 [title, msg, imgPath]
        val title = findViewById<TextView>(R.id.et_schedule_title).text.toString()
        val msg = findViewById<TextView>(R.id.et_schedule_message).text.toString()

        //레트로핏 작업
        val retrofit : Retrofit = RetrofitHelper.getRetrofitInstanceScalars()
        val retrofitService : RetrofitService= retrofit.create(RetrofitService::class.java)

        //이미지 파일을 MultiPartBody.Part로 포장 : @Part
        var filePart : MultipartBody.Part? = null
        if(imgPath!=null){
            var file = File(imgPath)
            val requestBody = RequestBody.create(MediaType.parse("image/*"),file)
            filePart= MultipartBody.Part.createFormData("img", file.name, requestBody)
            Toast.makeText(this, "${file.name}", Toast.LENGTH_SHORT).show()

        }

        //나머지 String 데이터들을 Map Collection에 저장 : @PartMap
        val dataPart = HashMap<String, String>()
        dataPart.put("title", title)
        dataPart.put("msg", msg)
        dataPart.put("date", date)

        val call : Call<String> = retrofitService.postDataToServer(dataPart, filePart)

        call.enqueue(object : Callback<String>{ //java의 new 키워드 대신에 object
            override fun onResponse(call: Call<String>, response: Response<String>) {
                //성공했을때
                var s : String? = response.body()
                Toast.makeText(this@CalendarSelectActivity, s+"", Toast.LENGTH_SHORT).show()
                //AlertDialog.Builder(this@CalendarSelectActivity).setMessage(""+s).create().show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                //실패했을때
                //Toast.makeText(this@CalendarSelectActivity, "error : ${t.message}", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@CalendarSelectActivity).setMessage(t.message).create().show()
            }

        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}