package org.example.project.helpers

import java.math.BigDecimal
import java.math.RoundingMode

 fun Double.toTwoDecimalPlaces(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}