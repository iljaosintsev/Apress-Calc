package com.turlir.calculator.interpreter;


import java.math.BigDecimal;

/**
 * Вычисляет выражение, записанное в определенной нотации
 */
public interface NotationInterpreter {

    /**
     *
     * @return промужеточный результат
     */
    BigDecimal poolDigit();

    /**
     * Записывает промежуточный результат
     * @param value результат
     */
    void pushDigit(BigDecimal value);

}
