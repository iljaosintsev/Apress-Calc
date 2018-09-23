package com.turlir.calculator.converter

import com.turlir.calculator.*
import com.turlir.calculator.member.Value
import org.junit.Assert.*
import org.junit.Test

class SequenceExpressionTest {

    @Test(expected = NoSuchElementException::class)
    fun emptyExp() {
        SequenceExpression(arrayListOf<Member>().iterator())
    }

    @Test
    fun multiValueCall() {
        val first = Value(4.0)
        val exp = SequenceExpression(seq(first))
        assertEquals(first, exp.value)
        assertEquals(first, exp.value)
    }

    @Test
    fun singleExp() {
        val first = Value(4.0)
        val exp = SequenceExpression(seq(first))
        checkLastExpression(exp)
    }

    @Test
    fun multiMember() {
        val exp = SequenceExpression(seq(Value(4.0), FPLUS, Value(2.0), FMULTI, FUNARY, Value(1.0)))
        for (i in 0 until 5) {
            assertFalse(exp.isLast())
            assertNotNull(exp.next)
        }
        checkLastExpression(exp)
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
        var exp: MathExpression = SequenceExpression(tokens.iterator())
        exp = exp.next!!
        val act = exp.inline()
        assertEquals(tokens.size - 1, act.size)
    }

    @Test
    fun inlineAtLast() {
        val tokens = listOf(Value(4.0), FPLUS, Value(2.0), FMULTI, FUNARY, Value(1.0))
        var exp: MathExpression = SequenceExpression(tokens.iterator())
        for (i in 0 until 5) {
            exp = exp.next!!
        }
        assertNotNull(exp)
        val actual = exp.inline()
        assertEquals(1, actual.size)
    }

    private fun checkLastExpression(exp: SequenceExpression) {
        assertTrue(exp.isLast())
        try {
            exp.next
            fail()
        } catch (e: Exception) {
            // ignored
        }
    }

}