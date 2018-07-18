package com.turlir.calculator.extractors;

import java.util.Iterator;

/**
 * Интерфейс для выделения из математического выражения отдельных подстрок, соответствующих его логическим
 * частям
 */
public interface IntervalExtractor {

    /**
     * Разделяет строку на операторы и операнды
     * @param value математическое выражение
     * @return последовательность подстрок
     */
    Iterator<Interval> iterator(String value);

}
