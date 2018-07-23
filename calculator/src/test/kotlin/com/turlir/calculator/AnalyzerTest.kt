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
        val act = analyzer.expression("2+3*4")!!
        val exp = analyzer.slice("2+3*4")
        val inline = act.inline()
        assertEquals(exp, inline)
    }

    @Test
    fun nullExpressionTest() {
        val act = analyzer.expression("")
        assertNull(act)
    }

    @Test
    fun sequenceExpressionTest() {
        val act = analyzer.sequenceExpression("2+3*4")!!
        val list = act.inline()
        assertEquals(5, list.size)
    }

    @Test(expected = java.util.NoSuchElementException::class)
    fun outOfSequenceExpressionTest() {
        var act: MathExpression = analyzer.sequenceExpression("2+3*4")!!
        for (i in 0..4) {
            println(act.value)
            act = act.next!!
        }
        val d = act.next
        assertNotNull(d)
        assertFalse(act.isLast())
    }

    @Test
    fun nullSequenceExpressionTest() {
        val act = analyzer.sequenceExpression("")
        assertNull(act)
    }
}