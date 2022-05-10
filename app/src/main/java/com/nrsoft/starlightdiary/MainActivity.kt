package com.nrsoft.starlightdiary

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.nrsoft.starlightdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val calendarFragment = CalendarFragment()
        val homeFragment = HomeFragment()
        val feelingFragment = FeelingFragment()


        replaceFragment(calendarFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.menu_bnv_calendar -> replaceFragment(calendarFragment)
                R.id.menu_bnv_home -> replaceFragment(homeFragment)
                R.id.menu_bnv_emotion -> replaceFragment(feelingFragment)
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.menu_bnv_home

        //동적퍼미션 요청//////////////////////////////////////
        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions,0);
        }
        //////////////////////////////////////////////////

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
    }

    //동적퍼미션 요청 처리 결과 받기///////////////////////////////////
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "외부저장소를 허용하셨습니다.", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "글쓰기가 금지됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
    ////////////////////////////////////////////


}