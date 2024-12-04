package com.tariq.taskproject.utils

import java.time.LocalTime

fun getTimeBasedGreeting(): String {
    val hour = LocalTime.now().hour
    return when (hour) {
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        in 21..23 -> "Good Night"
        else -> "Good Morning"
    }
}