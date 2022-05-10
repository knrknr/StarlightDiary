package com.nrsoft.starlightdiary

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.annotation.UiContext
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.nrsoft.starlightdiary.databinding.CalendarFragmentBinding
import com.nrsoft.starlightdiary.databinding.FeelingFragmentBinding
import com.nrsoft.starlightdiary.databinding.HomeFragmentBinding
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.math.log

class CalendarFragment : Fragment() {

    override fun onCreateView(//뷰가 만들어질때
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val binding: CalendarFragmentBinding by lazy { CalendarFragmentBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {//뷰가 만들어졌을때
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                //Toast.makeText(context, "$p1 / $p2 / $p3", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, CalendarSelectActivity::class.java)
                intent.putExtra("year",p1)
                intent.putExtra("month", p2)
                intent.putExtra("day", p3)
                startActivity(intent)
            }

        })

    }

}
