package com.turlir.calculator.converter

import com.turlir.calculator.FPLUS
import com.turlir.calculator.member.Value
import com.turlir.calculator.seq
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

class ExpressionTest {

    @Test
    fun byEmptyIteratorCheck() {
        val exp = Expression(seq())
        assertTrue(exp.isLast())
        assertEquals(0, exp.inline().size)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun valueFromEmptyIterator() {
        val exp = Expression(seq())
        exp.value
    }

    @Test
    fun fromIterator() {
        val example = listOf(Value(2.0), FPLUS, Value(3.0))
        val spy = Mockito.spy(seq(Value(2.0), FPLUS, Value(3.0)))
        val expression = Expression(spy)
        // check inline in constructor
        Mockito.verify(spy, times(3)).next()
        Mockito.verify(spy, times(4)).hasNext()
        // check result
        assertThat(expression.inline(), CoreMatchers.`is`(example))
        assertFalse(expression.isLast())
        for (token in example) {
            assertFalse(expression.isLast())
            assertEquals(token, expression.value)
        }
        // check is stop
        assertTrue(expression.isLast())
    }
}