package com.tariq.taskproject.data.remote

import com.tariq.taskproject.models.MedicalProblemsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("bfee72a5-51e8-4728-9b5c-cfcb38181995")
    suspend fun getMedications(): MedicalProblemsResponse
}