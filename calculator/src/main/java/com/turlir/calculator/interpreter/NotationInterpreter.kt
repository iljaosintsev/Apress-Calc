package com.turlir.calculator.interpreter


import java.math.BigDecimal

/**
 * Вычисляет выражение, записанное в определенной нотации
 */
interface NotationInterpreter {

    /**
     *
     * @return промужеточный результат
     */
    fun poolDigit(): BigDecimal

    /**
     * Записывает промежуточный результат
     * @param value результат
     */
    fun pushDigit(value: BigDecimal)

}
