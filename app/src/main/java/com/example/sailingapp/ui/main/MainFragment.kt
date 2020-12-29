package com.example.sailingapp.ui.main

import android.R.attr.*
import android.R.attr.progress
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.sailingapp.R
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        setSelectedDateTime()
        datePicker.setOnClickListener { displayDatePicker() }
        submitButton.setOnClickListener {  }
        slushButton.setOnClickListener { showSlush() }
    }

    private fun displayDatePicker() {
            val datePickerDialog =
                    DatePickerDialog(requireContext(), this, viewModel.year, viewModel.month, viewModel.day)
            datePickerDialog.show()
    }

    private fun showSlush() {
        dataGroup.visibility = View.INVISIBLE
        val rotation = loadAnimation(requireContext(), R.anim.rotation)
        rotation.fillAfter = true
        progressJie.startAnimation(rotation)
        progressJie.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed( {
            progressJie.clearAnimation()
            progressJie.visibility = View.GONE
            dataGroup.visibility = View.VISIBLE
        }, 2000)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.day = dayOfMonth
        viewModel.year = year
        viewModel.month = month

        viewModel.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.calendar.set(Calendar.MONTH, month)
        viewModel.calendar.set(Calendar.YEAR, year)

        val timePickerDialog = TimePickerDialog(requireContext(), this, viewModel.hour, viewModel.minute,
                DateFormat.is24HourFormat(requireContext()))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.hour = hourOfDay
        viewModel.minute = minute

        viewModel.calendar.set(Calendar.HOUR, hourOfDay)
        viewModel.calendar.set(Calendar.MINUTE, minute)

        setSelectedDateTime()
    }

    private fun setSelectedDateTime() {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateText = format.format(viewModel.calendar.time)
        datePicker.text = dateText
    }
}