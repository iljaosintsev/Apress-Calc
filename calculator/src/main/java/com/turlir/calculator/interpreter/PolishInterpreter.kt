package com.turlir.calculator.interpreter


import java.math.BigDecimal
import java.util.*

/**
 * Очередь промежуточных результатов [BigDecimal] для вычисления значения математического выражения
 */
class PolishInterpreter : NotationInterpreter {

    private val mQueue = ArrayDeque<BigDecimal>()

    override fun poolDigit() = mQueue.pop()!!

    override fun pushDigit(value: BigDecimal) {
        mQueue.push(value)
    }

}
