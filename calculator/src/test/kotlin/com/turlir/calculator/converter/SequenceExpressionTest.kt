package com.turlir.calculator.converter

import com.turlir.calculator.FMULTI
import com.turlir.calculator.FPLUS
import com.turlir.calculator.FUNARY
import com.turlir.calculator.member.Value
import com.turlir.calculator.seq
import org.junit.Assert.*
import org.junit.Test

class SequenceExpressionTest {

    @Test()
    fun emptyExp() {
        val exp = SequenceExpression(listOf<Member>().iterator())
        assertTrue(exp.isLast())
    }

    @Test
    fun multiValueCall() {
        val first = Value(4.0)
        val exp = SequenceExpression(seq(first))
        assertEquals(first, exp.value)
        assertTrue(exp.isLast())
    }

    @Test
    fun singleExp() {
        val first = Value(4.0)
        val exp = SequenceExpression(seq(first))
        assertEquals(first, exp.value)
        assertTrue(exp.isLast())
        try {
            exp.value
            fail()
        } catch (e: Exception) {
            // ignored
        }
    }

    @Test
    fun multiMember() {
        val exp = SequenceExpression(seq(Value(4.0), FPLUS, Value(2.0), FMULTI, FUNARY, Value(1.0)))
        do {
            assertNotNull(exp.value)
        } while (!exp.isLast())
    }

    @Test
    fun inlineAtFirst() {
        val tokens = listOf(Value(4.0), FPLUS, Value(2.0), FMULTI, FUNARY, Value(1.0))
        val exp = SequenceExpression(tokens.iterator())
        val act = exp.inline()
        assertEquals(tokens.size, act.size)
    }

    @Test
    fun inlineAtSecond() {
        val tokens = listOf(Value(4.0), FPLUS, Value(2.0), FMULTI, FUNARY, Value(1.0))
        val exp = SequenceExpression(tokens.iterator())
        assertNotNull(exp.value)
        val act = exp.inline()
        assertEquals(tokens.size - 1, act.size)
    }

    @Test
    fun inlineAtLast() {
        val tokens = listOf(Value(4.0), FPLUS, Value(2.0), FMULTI, FUNARY, Value(1.0))
        val exp: MathExpression = SequenceExpression(tokens.iterator())
        for (i in 0 until 5) {
             assertNotNull(exp.value)
        }
        val actual = exp.inline()
        assertEquals(1, actual.size)
    }

}