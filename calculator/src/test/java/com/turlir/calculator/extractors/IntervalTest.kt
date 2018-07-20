package com.turlir.calculator.extractors

import com.turlir.calculator.extractors.Interval
import org.junit.Assert.*
import org.junit.Test

class IntervalTest {

    @Test
    fun firstOperatorTest() {
        val p = Interval("23.54+45.67", 0)
        assertEquals("23.54", p.value)
        assertTrue(p.operand())
    }

    @Test
    fun operandTest() {
        val p = Interval("23.54+45.67", 5)
        assertEquals("+", p.value)
        assertFalse(p.operand())
    }

    @Test
    fun secondOperatorTest() {
        val p = Interval("23.54+45.67", 6)
        assertEquals("45.67", p.value)
        assertTrue(p.operand())
    }

}