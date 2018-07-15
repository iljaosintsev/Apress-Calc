package com.turlir.calculator.interpreter;


import java.math.BigDecimal;

/**
 * Вычисляет выражение, записанное в определенной нотации
 */
public interface NotationInterpreter {

    BigDecimal poolDigit();

    void pushDigit(BigDecimal value);

}
