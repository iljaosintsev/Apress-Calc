package com.turlir.calculator.converter;

import com.turlir.calculator.member.Operator;
import com.turlir.calculator.member.Value;

import java.util.Iterator;

/**
 * Выделяет участки выражения {@link Member} из строки
 */
public interface ExpressionExtractor {

    /**
     * Формирует итератор токенов выражения
     * @param value математическое выражение
     * @return итератор токенов {@link Value} или {@link Operator} выражения в прямом порядке
     * @throws IllegalArgumentException при работе итератора в случае ошибки разбора
     */
    Iterator<Member> iterator(String value);

}
