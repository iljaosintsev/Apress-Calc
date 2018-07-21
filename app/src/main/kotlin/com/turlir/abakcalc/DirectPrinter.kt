package com.turlir.abakcalc

import com.turlir.calculator.converter.Printer

import java.math.BigDecimal
import java.text.DecimalFormat

class DirectPrinter(private val mFormat: DecimalFormat) : Printer {

    private val mBuilder = StringBuilder()

    override fun append(part: String) {
        mBuilder.append(part)
    }

    override fun append(part: BigDecimal) {
        val str = mFormat.format(part)
        append(str)
    }

    override fun appendSeparator() {
        append(mFormat.decimalFormatSymbols.decimalSeparator.toString())
    }

    override fun length() = mBuilder.length

    override fun reset() {
        mBuilder.delete(0, length())
    }

    override fun toString() = mBuilder.toString()
}
