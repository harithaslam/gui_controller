package org.example.project.viewmodels

import androidx.lifecycle.ViewModel
import com.example.project.cache.SensorDataQueries
import io.github.koalaplot.core.xygraph.DefaultPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.pow

class AnalysisViewModel(private val sensorDataQueries: SensorDataQueries) :ViewModel() {
    private val _graphData = MutableStateFlow<List<DefaultPoint<Float, Float>>>(emptyList())
    val graphData: StateFlow<List<DefaultPoint<Float, Float>>> = _graphData

    fun generateGraphData() {
        // Simulate generating new data
        val newData = buildList {
            for (i in 1..1000) {
                add(DefaultPoint(i.toFloat(), (i.toFloat()).pow(3)))
            }
        }
        // Update the graph data
        _graphData.value = newData
    }
}