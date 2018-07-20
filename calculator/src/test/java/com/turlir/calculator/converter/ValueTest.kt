package com.turlir.calculator.converter

import com.turlir.calculator.member.Value
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ValueTest {

    private val mFormat = DecimalFormat()

    init {
        val settings = DecimalFormatSymbols(Locale("ru"))
        mFormat.decimalFormatSymbols = settings
        mFormat.maximumFractionDigits = 340 // see doc DecimalFormat
        mFormat.minimumIntegerDigits = 1
    }

    @Test
    fun findFormatPath() {
        val t = "1234.1234567890123456789"
        val bd = BigDecimal(t, MathContext.UNLIMITED)
        val format = mFormat.format(bd)
        assertEquals("1 234,1234567890123456789", format)
    }

    @Test
    fun displayTest() {
        val v = Value("1234.123456", false)
        val view = v.view()
        val printer = SimplePrinter(mFormat)
        view.print(printer)
        assertEquals("1 234,123456", printer.toString())
    }

    @Test
    fun lastSymbolSeparatorTest() {
        val v = Value("1234", true)
        val view = v.view()
        val printer = SimplePrinter(mFormat)
        view.print(printer)
        assertEquals("1 234,", printer.toString())
    }

    private inner class SimplePrinter constructor(private val mFormat: DecimalFormat) : Printer {

        private val mBuilder = StringBuilder()
        private val mSeparator: String = mFormat.decimalFormatSymbols.decimalSeparator.toString()

        override fun append(part: String) {
            mBuilder.append(part)
        }

        override fun append(part: BigDecimal) {
            val str = mFormat.format(part)
            append(str)
        }

        override fun appendSeparator() {
            append(mSeparator)
        }

        override fun length() = 0

        override fun reset() = throw UnsupportedOperationException()

        override fun toString() = mBuilder.toString()
    }
}