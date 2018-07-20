package com.turlir.calculator

import com.turlir.calculator.converter.MemberConverter
import com.turlir.calculator.extractors.ExpressionPartExtractor
import com.turlir.calculator.extractors.MultiOperatorExtractor
import com.turlir.calculator.interpreter.PolishInterpreter
import com.turlir.calculator.translator.PolishTranslator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IntegrationTest {

    private lateinit var calc: Calculator
    private lateinit var mAnalyzer: Analyzer

    @Before
    fun setup() {
        val conv = MemberConverter(
                MultiOperatorExtractor(
                        ExpressionPartExtractor()
                )
        )
        val trans = PolishTranslator()
        val inter = PolishInterpreter()
        calc = Calculator(trans, inter)
        mAnalyzer = Analyzer(conv)
    }

    @Test
    fun plusMinusTest() {
        val res = calc.calc(mAnalyzer.analyze("8 - 2 + 1")).toDouble()
        assertEquals(7.0, res, 0.1)
    }

    @Test
    fun multiplyDivideTest() {
        val res = calc.calc(mAnalyzer.analyze("4 * 4 / 8")).toDouble()
        assertEquals(2.0, res, 0.1)
    }

    @Test
    fun priorityTest() {
        val res = calc.calc(mAnalyzer.analyze("2 + 2 * 2")).toDouble()
        assertEquals(6.0, res, 0.1)
    }

    @Test
    fun floatingNumberTest() {
        val res = calc.calc(mAnalyzer.analyze("2,2 + 3")).toDouble()
        assertEquals(5.2, res, 0.1)
    }

    @Test
    fun complexTest() {
        var res: Double? = calc.calc(mAnalyzer.analyze("1 + 25 - 5 / 22")).toDouble()
        assertEquals(25.8, res!!, 0.1)

        res = calc.calc(mAnalyzer.analyze("1 + 25 - 5 / 22 + ( 45 + 34 )")).toDouble()
        assertEquals(104.8, res, 0.1)

        res = calc.calc(mAnalyzer.analyze("104.8 * -3")).toDouble()
        assertEquals(-314.4, res, 0.1)

        res = calc.calc(mAnalyzer.analyze("( 45 + 34 ) * -3")).toDouble()
        assertEquals(-237.0, res, 0.1)

        res = calc.calc(mAnalyzer.analyze("1 + 25 - 5 / 22 + ( 45 + 34 ) * -3")).toDouble()
        assertEquals(-211.23, res, 0.1)
    }

}