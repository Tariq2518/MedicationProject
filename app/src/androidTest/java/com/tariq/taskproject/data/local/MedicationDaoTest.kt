package com.tariq.taskproject.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tariq.taskproject.models.Medication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class MedicationDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var medicationDao: MedicationDao

    @Before
    fun setUp() {
        // Create in-memory database for testing
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        medicationDao = db.medicationDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAllShouldInsertMedicationsToDatabase() = runTest {

        val medications = listOf(
            Medication(name = "Med1", dose = "5mg", strength = "10mg", problem = "ProblemType")
        )

        // Act
        medicationDao.insertAll(medications)

        // Assert
        val storedMedications = medicationDao.getAllMedications().first()
        assertEquals(medications, storedMedications)
    }

    @Test
    fun getMedicationsByProblemShouldReturnFilteredListOfMedications() = runTest {

        val medications = listOf(
            Medication(name = "Med1", dose = "5mg", strength = "10mg", problem = "ProblemType"),
            Medication(name = "Med2", dose = "10mg", strength = "20mg", problem = "OtherProblem")
        )
        medicationDao.insertAll(medications)

        // Act
        val filteredMedications = medicationDao.getMedicationsByProblem("ProblemType").first()

        // Assert
        assertEquals(listOf(medications[0]), filteredMedications)
    }

    @Test
    fun getMedicationByIdShouldReturnMedicationByID() = runTest {
        val medication = Medication(id = 1, name = "Med1", dose = "5mg", strength = "10mg", problem = "ProblemType")
        medicationDao.insertAll(listOf(medication))

        // Act
        val storedMedication = medicationDao.getMedicationById(1)

        // Assert
        assertEquals(medication, storedMedication)
    }
}
