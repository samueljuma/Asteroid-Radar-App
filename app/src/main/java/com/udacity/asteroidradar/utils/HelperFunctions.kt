package com.udacity.asteroidradar.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//Helps to get current date
fun getCurrentDate(): String{
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(Date())
}

//Gets End Date after adding the said days
fun getEndDate(startDate: String): String{
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    val currentDate = dateFormat.parse(startDate)
    val calendar = Calendar.getInstance()
    calendar.time = currentDate!!
    calendar.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
    return dateFormat.format(calendar.time)
}