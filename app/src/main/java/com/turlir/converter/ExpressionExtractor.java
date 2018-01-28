package com.turlir.converter;

import java.util.Queue;

/**
 * Выделяет участки выражения {@link Member} из строки
 */
public interface ExpressionExtractor {

    Queue<Member> iterator(String value);

}
