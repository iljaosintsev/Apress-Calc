package com.turlir.calculator.converter

import com.turlir.calculator.interpreter.NotationInterpreter
import com.turlir.calculator.member.Operator
import com.turlir.calculator.member.Value

/**
 * Токен - участник математического выражения <br></br>
 * @see Value
 * @see Operator
 */
interface Member {

    /**
     *
     * @return является ли токен операндом (числом, [Operator])
     */
    fun operand(): Boolean

    /**
     * Обработать токен согласно нотации
     * @param interpreter интерпретатор нотации для обработки части математического выражения
     */
    fun process(interpreter: NotationInterpreter)

    /**
     *
     * @return экземпляр интерфейса для представления токена в [Printer]
     */
    fun view(): Visual

}
