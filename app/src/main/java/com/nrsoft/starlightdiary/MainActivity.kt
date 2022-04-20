package com.nrsoft.starlightdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nrsoft.starlightdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val fragments:MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragments.add(CalendarFragment())
        fragments.add(HomeFragment())
        fragments.add(FeelingFragment())

        supportFragmentManager.beginTransaction().add(R.id.container, fragments[0]).commit()

        binding.bnv.setOnItemSelectedListener{
            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }

            val tran= supportFragmentManager.beginTransaction()

            when(it.itemId) {
                R.id.menu_bnv_calendar->{
                    tran.show(fragments[0])
                }
                R.id.menu_bnv_home->{
                    tran.show(fragments[1])
                }
                R.id.menu_bnv_emotion->{
                    tran.show(fragments[2])
                }
            }
            tran.commit()

            true
        }
    }
}