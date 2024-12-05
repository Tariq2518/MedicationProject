package com.tariq.taskproject.presentation.screens.shared_viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tariq.taskproject.data.repository.MedicationRepository
import com.tariq.taskproject.models.Medication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class MedicationViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var medicationRepository: MedicationRepository

    private lateinit var medicationViewModel: MedicationViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Set up the Main dispatcher for tests
        Dispatchers.setMain(StandardTestDispatcher())

        // Initialize ViewModel with the mocked repository
        medicationViewModel = MedicationViewModel(medicationRepository)
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher after tests
        Dispatchers.resetMain()
    }


    @Test
    fun `fetchMedications should update medications when successful`() = runTest {

        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val mockMedications = listOf(
            Medication(name = "Med1", dose = "5mg", strength = "10mg", problem = "ProblemType")
        )
        `when`(medicationRepository.fetchAndStoreMedications()).thenReturn(mockMedications)
        `when`(medicationRepository.getLocalMedications()).thenReturn(flowOf(mockMedications))

        // Act
        medicationViewModel.fetchMedications()

        // Advance the dispatcher to process pending coroutines
        testScheduler.advanceUntilIdle()

        // Assert
        assertEquals(mockMedications, medicationViewModel.medications.value)
    }

}
