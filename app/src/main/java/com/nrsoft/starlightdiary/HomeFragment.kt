package com.nrsoft.starlightdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nrsoft.starlightdiary.databinding.CalendarFragmentBinding
import com.nrsoft.starlightdiary.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding:HomeFragmentBinding by lazy { HomeFragmentBinding.inflate(layoutInflater) }

}