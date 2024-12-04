package com.tariq.taskproject.utils

import com.google.gson.Gson

class DynamicJsonAdapter {
    companion object {
        fun createFlexibleGson(): Gson {
            return Gson().newBuilder()
                .setLenient()
                .create()
        }
    }
}