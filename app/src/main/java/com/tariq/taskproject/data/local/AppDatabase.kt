package com.tariq.taskproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tariq.taskproject.models.Medication

@Database(entities = [Medication::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
}