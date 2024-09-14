package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.line.LinePlot
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.DefaultPoint
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.autoScaleXRange
import io.github.koalaplot.core.xygraph.autoScaleYRange
import io.github.koalaplot.core.xygraph.rememberFloatLinearAxisModel

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun Graph(data: List<DefaultPoint<Float,Float>>){

    val dataExp = buildList {
        for (i in 1..1000) {
            add(DefaultPoint(i.toFloat(), (i * 2.1).toFloat()))
        }
    }

    // Apply an exponential function to each number in the list

    XYGraph(
        rememberFloatLinearAxisModel(data.autoScaleXRange()),
        rememberFloatLinearAxisModel(data.autoScaleYRange())
    ) {
        LinePlot(
            data,
            lineStyle = LineStyle(SolidColor(Color.Blue))
        )
        LinePlot(
            dataExp,
            lineStyle = LineStyle(
                SolidColor(Color(0, 200, 0)),
                2.dp,
                dashPathEffect(floatArrayOf(20f, 10f))
            )
        )
    }

}