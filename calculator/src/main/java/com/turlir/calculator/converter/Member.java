package com.turlir.calculator.converter;

import com.turlir.calculator.interpreter.NotationInterpreter;
import com.turlir.calculator.member.Operator;
import com.turlir.calculator.member.Value;

/**
 * Токен - участник математического выражения <br>
 * @see Value
 * @see Operator
 */
public interface Member {

    /**
     *
     * @return является ли токен операндом (числом, {@link Operator})
     */
    boolean operand();

    /**
     * Обработать токен согласно нотации
     * @param interpreter интерпретатор нотации для обработки части математического выражения
     */
    void process(NotationInterpreter interpreter);

    /**
     *
     * @return экземпляр интерфейса для представления токена в {@link Printer}
     */
    Visual view();

}
