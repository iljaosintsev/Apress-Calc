package com.turlir.calculator

import com.turlir.calculator.converter.MemberConverter
import com.turlir.calculator.interpreter.PolishInterpreter
import com.turlir.calculator.translator.PolishTranslator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IntegrationTest {

    private lateinit var calc: Calculator

    @Before
    fun setup() {
        val conv = MemberConverter()
        val analyzer = Analyzer(conv)
        val trans = PolishTranslator()
        val inter = PolishInterpreter()
        calc = Calculator(trans, inter, analyzer)
    }

    @Test
    fun plusMinusTest() {
        val res = calc.calc("8 - 2 + 1").toDouble()
        assertEquals(7.0, res, 0.1)
    }

    @Test
    fun multiplyDivideTest() {
        val res = calc.calc("4 * 4 / 8").toDouble()
        assertEquals(2.0, res, 0.1)
    }

    @Test
    fun priorityTest() {
        val res = calc.calc("2 + 2 * 2").toDouble()
        assertEquals(6.0, res, 0.1)
    }

    @Test
    fun floatingNumberTest() {
        val res = calc.calc("2,2 + 3").toDouble()
        assertEquals(5.2, res, 0.1)
    }

    @Test
    fun complexTest() {
        var res: Double? = calc.calc("1 + 25 - 5 / 22").toDouble()
        assertEquals(25.8, res!!, 0.1)

        res = calc.calc("1 + 25 - 5 / 22 +  45 + 34 )").toDouble()
        assertEquals(104.8, res, 0.1)

        res = calc.calc("104.8 * -3").toDouble()
        assertEquals(-314.4, res, 0.1)

        res = calc.calc("( 45 + 34 ) * -").toDouble()
        assertEquals(-237.0, res, 0.1)

        res = calc.calc("1 + 25 - 5 / 22 +  45 + 34 ) * -3").toDouble()
        assertEquals(-211.23, res, 0.1)
    }

}