package com.tariq.taskproject.presentation.screens.greeting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tariq.taskproject.models.Medication
import com.tariq.taskproject.presentation.components.MedicationDetailsCard
import com.tariq.taskproject.presentation.screens.shared_viewmodel.MedicationViewModel
import com.tariq.taskproject.utils.getTimeBasedGreeting

@Composable
fun DashboardScreen(
    username: String,
    medicationViewModel: MedicationViewModel,
    onMedicationClick: (Medication) -> Unit
) {
    val medications by medicationViewModel.medications.collectAsState()

    LaunchedEffect(true) {
        medicationViewModel.fetchMedications()
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 16.dp, vertical = 30.dp)
    ) {
        Text(
            text = "Hello, $username!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = getTimeBasedGreeting(),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (medications.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else{
            LazyColumn {
                items(
                    items= medications,
                    key = { item ->
                        item.id
                    }
                ) { medication ->
                    MedicationDetailsCard(
                        medication = medication,
                        onClick = { onMedicationClick(medication) }
                    )
                    HorizontalDivider()
                }
            }
        }

    }
}

@Preview
@Composable
fun DashboardScreenPreview(){
    DashboardScreen(
        username = "Tariq",
        medicationViewModel = hiltViewModel(),
        onMedicationClick = { }
    )
}