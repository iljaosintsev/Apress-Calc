package com.turlir.calculator

import com.turlir.calculator.converter.*
import java.util.*

/**
 * Анализирует математическое выражение. Выражение может быть не полным, но должно содержать только известные
 * операторы и [Double] операнды, представленные в виде [Member]
 */
class Analyzer {

    private val mConverter: ExpressionExtractor = MemberConverter()

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return последовательность токенов выражения
     * @throws IllegalArgumentException при работе итератора в случае ошибки разбора
     */
    fun analyze(exp: String): Iterator<Member> = mConverter.iterator(exp)

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return список из токенов выражения
     * @throws IllegalArgumentException в случае ошибки разбора
     */
    @Throws(IllegalArgumentException::class)
    fun slice(exp: String): List<Member> {
        val primaryQ = analyze(exp)
        val copy = LinkedList<Member>()
        while (primaryQ.hasNext()) {
            val item = primaryQ.next()
            copy.add(item)
        }
        return copy
    }

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return связный список из токенов математического выражения или null если выражение пустое
     * @throws IllegalArgumentException в случае ошибки разбора
     */
    @Throws(IllegalArgumentException::class)
    fun expression(exp: String): MathExpression? {
        val primaryQ = analyze(exp)

        if(!primaryQ.hasNext()) return null
        val first = Expression(primaryQ.next())
        var current = first

        while (primaryQ.hasNext()) {
            val item = primaryQ.next()
            current = current.next(item)
        }
        return first
    }

}
