package com.turlir.calculator.extractors

import java.util.*
import java.util.regex.Pattern

/**
 * Доразделяет выражение на операторы (синус, консинус, тангенс, умножить, разделить, сложить, вычесть) и операнды
 * <br></br>
 * В процессе работы интератора при ошибке разрабока может быть вызвано [IllegalArgumentException]
 */
class MultiOperatorExtractor
/**
 *
 * @param parent делегат – алгоритм упрощенного разделения выражения
 */
(private val parent: ExpressionPartExtractor) : IntervalExtractor {

    override fun iterator(value: String): Iterator<Interval> {
        return MultiOperatorIterator(parent.iterator(value))
    }

    private class MultiOperatorIterator constructor(private val parent: Iterator<Interval>) : Iterator<Interval> {

        companion object {
            private val OPERATORS = Pattern.compile("sin\\(|cos\\(|tag\\(|[(]|[)]|\\*|-|/|\\+")
        }

        private var mSplit: Queue<Interval> = LinkedList()

        @Throws(IllegalArgumentException::class)
        override fun next(): Interval {
            if (!mSplit.isEmpty()) {
                return mSplit.poll()
            }
            val i = parent.next()
            if (!i.operand()) {
                if (i.value.length > 1) {
                    mSplit = split(i.value)
                    return mSplit.poll()
                }
            }
            return i
        }

        override fun hasNext(): Boolean = parent.hasNext() || !mSplit.isEmpty()

        private fun split(o: String): Queue<Interval> {
            val matcher = OPERATORS.matcher(o)
            if (matcher.find()) {
                val arr = LinkedList<Interval>()
                do {
                    arr.add(Interval(false, matcher.group()))
                } while (matcher.find())
                return arr
            } else {
                throw IllegalArgumentException("неизвестный оператор $o")
            }
        }
    }
}
