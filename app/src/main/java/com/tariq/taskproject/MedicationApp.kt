package com.tariq.taskproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MedicationApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}