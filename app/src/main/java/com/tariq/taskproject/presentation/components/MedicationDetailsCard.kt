package com.tariq.taskproject.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tariq.taskproject.models.Medication

@Composable
fun MedicationDetailsCard(
    medication: Medication,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = medication.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Dose: ${medication.dose}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Strength: ${medication.strength}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Problem: ${medication.problem}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun MedicationDetailsCardPreview(){
    MedicationDetailsCard(
        medication = Medication(name = "Asthama", dose = "Test Dose", strength = "Test Strength", problem = "Problem test")
    ) { }
}