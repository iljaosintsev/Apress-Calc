package com.turlir.calculator

import com.turlir.calculator.interpreter.PolishInterpreter
import com.turlir.calculator.translator.PolishTranslator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IntegrationTest {

    private lateinit var calc: Calculator

    @Before
    fun setup() {
        val analyzer = Analyzer()
        val trans = PolishTranslator()
        val inter = PolishInterpreter()
        calc = Calculator(analyzer, trans, inter)
    }

    @Test
    fun plusMinusTest() {
        val res = calc.calc(calc.direct("8 - 2 + 1")).toDouble()
        assertEquals(7.0, res, 0.1)
    }

    @Test
    fun multiplyDivideTest() {
        val res = calc.calc(calc.direct("4 * 4 / 8")).toDouble()
        assertEquals(2.0, res, 0.1)
    }

    @Test
    fun priorityTest() {
        val res = calc.calc(calc.direct("2 + 2 * 2")).toDouble()
        assertEquals(6.0, res, 0.1)
    }

    @Test
    fun floatingNumberTest() {
        val res = calc.calc(calc.direct("2,2 + 3")).toDouble()
        assertEquals(5.2, res, 0.1)
    }

    @Test
    fun complexTest() {
        var res: Double? = calc.calc(calc.direct("1 + 25 - 5 / 22")).toDouble()
        assertEquals(25.8, res!!, 0.1)

        res = calc.calc(calc.direct("1 + 25 - 5 / 22 +  45 + 34")).toDouble()
        assertEquals(104.8, res, 0.1)

        res = calc.calc(calc.direct("104.8 * -3")).toDouble()
        assertEquals(-314.4, res, 0.1)

        res = calc.calc(calc.direct("( 45 + 34 ) * -1")).toDouble()
        assertEquals(-79.0, res, 0.1)

        res = calc.calc(calc.direct("1 + 25 - 5 / 22 + ( 45 + 34 ) * -3")).toDouble()
        assertEquals(-211.23, res, 0.1)
    }

}