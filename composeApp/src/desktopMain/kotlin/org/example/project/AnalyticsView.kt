package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.viewmodels.AnalysisViewModel
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun AnalyticsView(viewModel: AnalysisViewModel = viewModel{ AnalysisViewModel(sensorDataQueries = getKoin().get()) }){
    val graphData by viewModel.graphData.collectAsState()
    Column(Modifier.padding(5.dp)) {
        Button(shape = RoundedCornerShape(25.dp),
            onClick =
            {viewModel.generateGraphData()}
            , modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)){
            Text("Generate Line Graphs")
        }
        Graph(graphData)
    }
}