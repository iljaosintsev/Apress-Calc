package com.turlir.calculator.extractors

/**
 * Интерфейс для выделения из математического выражения отдельных подстрок, соответствующих его логическим
 * частям
 */
interface IntervalExtractor {

    /**
     * Разделяет строку на операторы и операнды
     * @param value математическое выражение
     * @return последовательность подстрок
     */
    fun iterator(value: String): Iterator<Interval>

}
