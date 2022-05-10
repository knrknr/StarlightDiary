package com.nrsoft.starlightdiary

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){


    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
       return when (position){
           0 -> CalendarFragment()
           1 -> HomeFragment()
           else -> FeelingFragment()
       }
    }


}