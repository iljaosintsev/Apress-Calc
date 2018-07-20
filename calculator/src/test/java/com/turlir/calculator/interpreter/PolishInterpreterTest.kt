package com.turlir.calculator.interpreter

import com.turlir.calculator.interpreter.NotationInterpreter
import com.turlir.calculator.interpreter.PolishInterpreter
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class PolishInterpreterTest {

    private var mInter: NotationInterpreter = PolishInterpreter()

    @Test
    fun singlePoolTest() {
        mInter = PolishInterpreter()
        mInter.pushDigit(BigDecimal(2.0))
        val actual = mInter.poolDigit()
        assertTrue(BigDecimal(2.0).compareTo(actual) == 0)
    }

    @Test
    fun multiPollTest() {
        val array = (1..5).asSequence()
                .map { BigDecimal(it) }
                .toList()

        mInter = PolishInterpreter()
        for (digit in array) {
            mInter.pushDigit(digit)
        }

        for (i in array.indices) {
            val digit = array[array.size - i - 1]
            val actual = mInter.poolDigit()
            assertTrue(digit.compareTo(actual) == 0)
        }
    }

}