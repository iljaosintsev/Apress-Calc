package com.turlir.calculator.converter

import com.turlir.calculator.member.Operator
import com.turlir.calculator.member.Value

/**
 * Выделяет участки выражения [Member] из строки
 */
interface ExpressionExtractor {

    /**
     * Формирует итератор токенов выражения
     * @param value математическое выражение
     * @return итератор токенов [Value] или [Operator] выражения в прямом порядке
     * @throws IllegalArgumentException при работе итератора в случае ошибки разбора
     */
    fun iterator(value: String): Iterator<Member>

}
