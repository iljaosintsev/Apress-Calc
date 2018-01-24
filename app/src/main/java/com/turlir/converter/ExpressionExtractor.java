package com.turlir.converter;

import java.util.Iterator;

/**
 * Выделяет участки выражения {@link Member} из строки
 */
public interface ExpressionExtractor {

    Iterator<Member> iterator(String value);

}
