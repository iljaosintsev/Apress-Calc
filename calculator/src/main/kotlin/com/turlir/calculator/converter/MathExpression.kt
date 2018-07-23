package com.turlir.calculator.converter

/**
 * Интерфейс описывает связыный список из токенов математического выражения
 */
interface MathExpression {

    /**
     * Токен выражения
     */
    val value: Member

    /**
     * Следующий контейнер
     */
    val next: MathExpression?

    /**
     * Преобразование в связный список
     */
    fun inline(): List<Member>

    /**
     * Является ли контейнер последним
     */
    fun isLast(): Boolean

}