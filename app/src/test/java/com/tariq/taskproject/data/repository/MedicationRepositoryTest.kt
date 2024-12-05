package com.tariq.taskproject.data.repository

import android.util.Log
import com.tariq.taskproject.data.local.MedicationDao
import com.tariq.taskproject.data.remote.ApiService
import com.tariq.taskproject.models.Drug
import com.tariq.taskproject.models.MedicalCondition
import com.tariq.taskproject.models.MedicalProblemsResponse
import com.tariq.taskproject.models.Medication
import com.tariq.taskproject.models.MedicationInfo
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MedicationRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var medicationDao: MedicationDao

    private lateinit var medicationRepository: MedicationRepository

    @Before
    fun setUp() {
        mockStatic(Log::class.java).use { mockedLog ->
            mockedLog.`when`<Int> { Log.i(anyString(), anyString()) }.thenReturn(0)

        }
        MockitoAnnotations.openMocks(this)

        medicationRepository = MedicationRepository(apiService, medicationDao)

    }
    @Test
    fun `fetchAndStoreMedications should return list of medications when successful`() = runTest {

        val mockResponse = MedicalProblemsResponse(
            problems = listOf(
                mapOf(
                    "ProblemType" to listOf(
                        MedicalCondition(
                            medications = listOf(
                                MedicationInfo(
                                    listOf(
                                        mapOf(
                                            "class" to listOf(
                                                mapOf(
                                                    "name" to listOf(
                                                        Drug(name = "Med1", dose = "5mg", strength = "10mg")
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        val expectedMedications = listOf(
            Medication(name = "Med1", dose = "5mg", strength = "10mg", problem = "ProblemType")
        )

        // Mock API call
        `when`(apiService.getMedications()).thenReturn(mockResponse)

        // Mock DAO insertAll call (use `thenAnswer` to simulate behavior)
        doAnswer { Unit }.`when`(medicationDao).insertAll(expectedMedications)

        // Act
        val result = medicationRepository.fetchAndStoreMedications()

        // Assert
        assertEquals(expectedMedications, result)
        verify(medicationDao).insertAll(expectedMedications) // Verify DAO was called
    }


    @Test
    fun `getMedicationById should return medication when found`() = runTest {

        val medication = Medication(id = 1, name = "Med1", dose = "5mg", strength = "10mg", problem = "ProblemType")
        `when`(medicationDao.getMedicationById(1)).thenReturn(medication)

        // Act
        val result = medicationRepository.getMedicationById(1)

        // Assert
        assertEquals(medication, result)
    }

    @Test
    fun `getMedicationById should return null when not found`() = runTest {

        `when`(medicationDao.getMedicationById(2)).thenReturn(null)

        // Act
        val result = medicationRepository.getMedicationById(2)

        // Assert
        assertNull(result)
    }
}
