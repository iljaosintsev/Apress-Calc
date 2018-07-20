package com.turlir.calculator.translator

import com.turlir.calculator.converter.Member
import com.turlir.calculator.converter.Visual
import com.turlir.calculator.interpreter.NotationInterpreter
import com.turlir.calculator.member.Operator
import com.turlir.calculator.member.Operators
import com.turlir.calculator.member.Value
import com.turlir.calculator.translator.NotationTranslator
import com.turlir.calculator.translator.PolishTranslator
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.*

class PolishTranslatorTest {

    private val OS = Operators.OS

    private val CS = Operators.CS

    private val PLUS = StubOperator("+", 2)

    private val MINUS = StubOperator("-", 2)

    private val MULTI = StubOperator("*", 3)

    private val DIV = StubOperator("/", 3)

    private val UNARY = StubOperator("-", 4)

    private fun check(actual: Queue<Member>, vararg exp: Member) {
        assertNotNull(actual)
        assertEquals(exp.size.toLong(), actual.size.toLong())
        assertThat(actual, CoreMatchers.hasItems(*exp))
    }

    private fun seq(vararg member: Member) = listOf(*member).iterator()

    //

    private lateinit var sor: NotationTranslator

    @Before
    fun setup() {
        sor = PolishTranslator()
    }

    @Test
    @Throws(Exception::class)
    fun plusTest() {
        val sequence = seq(Value(2.0), PLUS, Value(3.0))
        val lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(3.0), PLUS)
    }

    @Test
    @Throws(Exception::class)
    fun priorityTest() {
        val sequence = seq(Value(2.0), PLUS, Value(3.0), MULTI, Value(7.0), MINUS, Value(4.0), DIV, Value(2.0))
        val lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(3.0), Value(7.0), MULTI, PLUS, Value(4.0), Value(2.0), DIV, MINUS)
    }

    @Test
    @Throws(Exception::class)
    fun bracketTest() {
        var sequence = seq(Value(2.0), PLUS, OS, Value(6.0), MINUS, Value(5.0), CS)
        var lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(6.0), Value(5.0), MINUS, PLUS)

        sequence = seq(Value(2.0), PLUS, OS, Value(6.0), MINUS, Value(4.0), CS, MULTI, Value(3.0))
        lst = sor.translate(sequence)
        check(lst, Value(2.0), Value(6.0), Value(4.0), MINUS, Value(3.0), MULTI, PLUS)
    }

    @Test
    @Throws(Exception::class)
    operator fun unaryMinus() {
        val sequence = seq(UNARY, Value(4.0), PLUS, Value(2.0))
        val lst = sor.translate(sequence)
        check(lst, Value(4.0), UNARY, Value(2.0), PLUS)
    }

    @Test
    @Throws(Exception::class)
    fun unaryMinusInsideExp() {
        val sequence = seq(Value(4.0), PLUS, Value(2.0), MULTI, UNARY, Value(1.0))
        val lst = sor.translate(sequence)
        check(lst, Value(4.0), Value(2.0), MULTI, PLUS, Value(1.0), UNARY)
    }

    private class StubOperator constructor(token: String, priority: Int) : Operator(token, priority) {

        override fun process(interpreter: NotationInterpreter) {
            throw IllegalStateException()
        }

        override fun view(): Visual {
            throw IllegalStateException()
        }
    }

}