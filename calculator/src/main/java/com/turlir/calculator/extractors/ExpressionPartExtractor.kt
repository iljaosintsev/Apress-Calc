package com.turlir.calculator.extractors

/**
 * Простое выделение из математического выражения операторов и операндов <br></br>
 * Выполняет чистку строкового представления выражения
 */
class ExpressionPartExtractor : IntervalExtractor {

    override fun iterator(value: String): Iterator<Interval> {
        return IntervalIterator(value)
    }

    private class IntervalIterator constructor(str: String) : Iterator<Interval> {

        companion object {
            private val oneRegex = "\\s+".toRegex()
            private val twoRegex = ",".toRegex()
        }

        private val mStr: String = str.replace(oneRegex, "").replace(twoRegex, ".")
        private var mLength: Int = 0

        override fun next(): Interval {
            val part = Interval(mStr, mLength)
            mLength += part.value.length
            return part
        }

        override fun hasNext() = mLength < mStr.length

    }
}
