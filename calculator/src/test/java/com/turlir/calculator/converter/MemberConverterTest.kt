package com.turlir.calculator.converter

import com.turlir.calculator.extractors.CommonTest
import com.turlir.calculator.extractors.MultiOperatorExtractor
import com.turlir.calculator.member.Operators
import com.turlir.calculator.member.Value
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MemberConverterTest : CommonTest() {

    @Mock
    lateinit var extractor: MultiOperatorExtractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAllMembers() {
        val seq = Arrays.asList(
                or("+"),
                or("-"),
                or("*"),
                or("/"),
                or("("),
                or(")"),
                and("3"),
                or("-")
        ).iterator()
        Mockito.`when`(extractor.iterator(Mockito.anyString())).thenReturn(seq)
        val conv = MemberConverter(extractor).iterator("")
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
        val seq = Arrays.asList(
                or("-"),
                and("4"),
                or("+"),
                and("2")
        ).iterator()
        Mockito.`when`(extractor.iterator(Mockito.anyString())).thenReturn(seq)
        val conv = MemberConverter(extractor).iterator("")
        or(conv, Operators.UNARY_MINUS)
        and(conv, Value(4.0))
        or(conv, Operators.PLUS)
        and(conv, Value(2.0))
        assertFalse(conv.hasNext())
    }

    @Test
    fun unaryMinusInsideExp() {
        val seq = Arrays.asList(
                and("4"),
                or("+"),
                and("2"),
                or("*"),
                or("-"),
                and("1")
        ).iterator()
        Mockito.`when`(extractor.iterator(Mockito.anyString())).thenReturn(seq)
        val conv = MemberConverter(extractor).iterator("")
        and(conv, Value(4.0))
        or(conv, Operators.PLUS)
        and(conv, Value(2.0))
        or(conv, Operators.MULTI)
        or(conv, Operators.UNARY_MINUS)
        and(conv, Value(1.0))
        assertFalse(conv.hasNext())
    }

}