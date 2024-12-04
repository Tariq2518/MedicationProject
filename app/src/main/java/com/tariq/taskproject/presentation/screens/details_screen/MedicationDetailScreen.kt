package com.tariq.taskproject.presentation.screens.details_screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tariq.taskproject.models.Medication
import com.tariq.taskproject.presentation.components.DetailRow
import com.tariq.taskproject.presentation.screens.shared_viewmodel.MedicationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationDetailScreen(
    medicationId: Int,
    viewModel: MedicationViewModel,
    onBackClick: () -> Unit
) {
    var medication by remember { mutableStateOf<Medication?>(null) }
    LaunchedEffect(true) {
        medication = withContext(Dispatchers.IO) {
            viewModel.getMedicationById(medicationId)
        }
        Log.i("TAG", "MedicationDetailScreen: $medication")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medication Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Crossfade(
            targetState = medication,
            label = "Medication Details",
            modifier = Modifier.padding(paddingValues)
        ) { med ->
            if (med != null) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            // Use AnimatedContent for smoother text transitions
                            AnimatedContent(
                                targetState = med.name,
                                label = "Medication Name"
                            ) { name ->
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            DetailRow("Problem", med.problem)
                            DetailRow("Dose", med.dose)
                            DetailRow("Strength", med.strength)
                        }
                    }
                }
            } else {
                // Skeleton loading state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}



@Preview
@Composable
fun DetailScreePreview(){
    MedicationDetailScreen(
        medicationId = 1223,
        viewModel = hiltViewModel(),
        onBackClick = {}
    )
}