package com.tariq.taskproject.data.repository

import android.util.Log
import com.tariq.taskproject.data.local.MedicationDao
import com.tariq.taskproject.data.remote.ApiService
import com.tariq.taskproject.models.MedicalProblemsResponse
import com.tariq.taskproject.models.Medication
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicationRepository @Inject constructor(
    private val apiService: ApiService,
    private val medicationDao: MedicationDao
) {
    suspend fun fetchAndStoreMedications(): List<Medication> {
        try {
            val response = apiService.getMedications()
        val medications = parseMedicationsFromResponse(response)

        medicationDao.insertAll(medications)

        return medications
        }
        catch (e: Exception){
            Log.i("Response", "fetchAndStoreMedications: $e")
            return emptyList()
        }
    }

    fun getLocalMedications(): Flow<List<Medication>> = medicationDao.getAllMedications()

    suspend fun getMedicationById(id: Int): Medication? = medicationDao.getMedicationById(id = id)

    private fun parseMedicationsFromResponse(response: MedicalProblemsResponse): List<Medication> {
        Log.i("Response", "Number of problems: ${response.problems.size}")

        val medications = mutableListOf<Medication>()
        response.problems.forEach { problemMap ->
            problemMap.forEach { (problemType, conditions) ->
                conditions.forEach { condition ->
                    condition.medications?.forEach { medication ->
                        medication.medicationsClasses?.forEach { classContainer ->
                            classContainer.forEach { (_, classNameList) ->
                                classNameList.forEach { drugContainer ->
                                    drugContainer.forEach { (drugKey, drugList) ->
                                        drugList.forEach { drug ->
                                            if (!drug.name.isNullOrBlank() &&
                                                !drug.strength.isNullOrBlank()) {
                                                medications.add(
                                                    Medication(
                                                        name = drug.name,
                                                        dose = drug.dose ?: "-",
                                                        strength = drug.strength,
                                                        problem = problemType
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.i("Response", "Parsed Medications count: ${medications.size}")
        return medications
    }


}