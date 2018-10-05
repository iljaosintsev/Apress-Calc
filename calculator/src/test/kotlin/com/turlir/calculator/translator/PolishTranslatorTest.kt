package com.turlir.calculator.translator

import com.turlir.calculator.*
import com.turlir.calculator.converter.Member
import com.turlir.calculator.member.Value
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.*

class PolishTranslatorTest {

    private fun check(actual: Queue<Member>, vararg exp: Member) {
        assertNotNull(actual)
        assertEquals(exp.size.toLong(), actual.size.toLong())
        assertThat(actual, CoreMatchers.hasItems(*exp))
    }

    //

    private lateinit var sor: NotationTranslator

    @Before
    fun setup() {
        sor = PolishTranslator()
    }

    @Test(expected = Exception::class)
    fun plusTest() {
        val sequence = seq(Value(2.0), FOS, Value(3.0))
        val lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(3.0), FPLUS)
    }

    @Test
    @Throws(Exception::class)
    fun priorityTest() {
        val sequence = seq(Value(2.0), FPLUS, Value(3.0), FMULTI, Value(7.0), FMINUS, Value(4.0), FDIV, Value(2.0))
        val lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(3.0), Value(7.0), FMULTI, FPLUS, Value(4.0), Value(2.0), FDIV, FMINUS)
    }

    @Test
    @Throws(Exception::class)
    fun bracketTest() {
        var sequence = seq(Value(2.0), FPLUS, FOS, Value(6.0), FMINUS, Value(5.0), FCS)
        var lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(6.0), Value(5.0), FMINUS, FPLUS)

        sequence = seq(Value(2.0), FPLUS, FOS, Value(6.0), FMINUS, Value(4.0), FCS, FMULTI, Value(3.0))
        lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(6.0), Value(4.0), FMINUS, Value(3.0), FMULTI, FPLUS)
    }

    @Test
    @Throws(Exception::class)
    fun unaryMinus() {
        val sequence = seq(FUNARY, Value(4.0), FPLUS, Value(2.0))
        val lst = sor.translate(sequence)
        check(lst, Value(4.0), FUNARY, Value(2.0), FPLUS)
    }

    @Test
    @Throws(Exception::class)
    fun unaryMinusInsideExp() {
        val sequence = seq(Value(4.0), FPLUS, Value(2.0), FMULTI, FUNARY, Value(1.0))
        val lst = sor.translate(sequence)
        check(lst, Value(4.0), Value(2.0), FMULTI, FPLUS, Value(1.0), FUNARY)
    }
}