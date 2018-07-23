package com.turlir.calculator

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
        val exp = analyzer.slice("2+3*4")

        val act = analyzer.expression("2+3*4")
        Assert.assertNotNull(act)
        val inline = act?.inline()
        assertEquals(exp, inline)
    }

    @Test
    fun nullExpressionTest() {
        val act = analyzer.expression("")
        Assert.assertNull(act)
    }

}