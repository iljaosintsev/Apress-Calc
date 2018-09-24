package com.turlir.calculator

import com.turlir.calculator.converter.MathExpression
import org.junit.Assert.*
import org.junit.Test

class AnalyzerTest {

    private val analyzer = Analyzer()

    @Test
    fun emptySliceTest() {
        val slice = analyzer.slice("")
        assertEquals(0, slice.size)
    }

    @Test
    fun emptyAnalyzeTest() {
        val analyze = analyzer.analyze("")
        assertFalse(analyze.hasNext())
    }

    @Test
    fun expressionTest() {
        val act = analyzer.expression("2+3*4")
        val exp = analyzer.slice("2+3*4")
        val inline = act.inline()
        assertEquals(exp, inline)
    }

    @Test(expected = EmptyExpressionException::class)
    fun emptyExpressionTest() {
        analyzer.expression("")
    }

    @Test
    fun sequenceExpressionTest() {
        val act = analyzer.sequenceExpression("2+3*4")
        val list = act.inline()
        assertEquals(5, list.size)
    }

    @Test
    fun outOfSequenceExpressionTest() {
        val act: MathExpression = analyzer.sequenceExpression("2+3*4")
        for (i in 0 until 4) {
            assertNotNull(act.value)
        }
        val d = act.value
        println(d)
    }

    @Test(expected = EmptyExpressionException::class)
    fun emptySequenceExpressionTest() {
        analyzer.sequenceExpression("")
    }
}