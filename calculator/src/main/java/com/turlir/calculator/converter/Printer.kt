package com.turlir.calculator.converter

import java.math.BigDecimal

interface Printer {

    fun append(part: String)

    fun append(part: BigDecimal)

    fun appendSeparator()

    fun length(): Int

    fun reset()
}
