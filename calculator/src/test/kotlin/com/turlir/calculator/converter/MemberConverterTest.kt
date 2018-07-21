package com.turlir.calculator.converter

import com.turlir.calculator.extractors.CommonTest
import com.turlir.calculator.member.Operators
import com.turlir.calculator.member.Value
import org.junit.Assert.assertFalse
import org.junit.Test

class MemberConverterTest : CommonTest() {

    @Test
    fun testAllMembers() {
        val conv = MemberConverter().iterator("+-*/()3-")
        or(conv, Operators.PLUS)
        or(conv, Operators.UNARY_MINUS)
        or(conv, Operators.MULTI)
        or(conv, Operators.DIV)
        or(conv, Operators.OS)
        or(conv, Operators.CS)
        and(conv, Value(3.0))
        or(conv, Operators.MINUS)
        assertFalse(conv.hasNext())
    }

    @Test
    operator fun unaryMinus() {
        val conv = MemberConverter().iterator("-4+2")
        or(conv, Operators.UNARY_MINUS)
        and(conv, Value(4.0))
        or(conv, Operators.PLUS)
        and(conv, Value(2.0))
        assertFalse(conv.hasNext())
    }

    @Test
    fun unaryMinusInsideExp() {
        val conv = MemberConverter().iterator("4+2*-1")
        and(conv, Value(4.0))
        or(conv, Operators.PLUS)
        and(conv, Value(2.0))
        or(conv, Operators.MULTI)
        or(conv, Operators.UNARY_MINUS)
        and(conv, Value(1.0))
        assertFalse(conv.hasNext())
    }

}