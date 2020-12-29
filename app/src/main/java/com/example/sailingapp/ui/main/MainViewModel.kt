package com.example.sailingapp.ui.main

import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    val calendar: Calendar = Calendar.getInstance()
    var minute: Int = calendar.get(Calendar.MINUTE)
    var hour: Int = calendar.get(Calendar.HOUR)
    var day = calendar.get(Calendar.DAY_OF_MONTH)
    var month = calendar.get(Calendar.MONTH)
    var year = calendar.get(Calendar.YEAR)
}