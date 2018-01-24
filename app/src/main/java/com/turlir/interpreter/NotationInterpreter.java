package com.turlir.interpreter;


/**
 * Вычисляет выражение, записанное в определенной нотации
 */
public interface NotationInterpreter {

    double poolDigit();

    void pushDigit(double value);

}
