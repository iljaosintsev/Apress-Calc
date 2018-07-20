package com.turlir.calculator.converter

import com.turlir.calculator.extractors.ExpressionPartExtractor
import com.turlir.calculator.extractors.Interval
import com.turlir.calculator.extractors.MultiOperatorExtractor
import com.turlir.calculator.member.Operators
import com.turlir.calculator.member.Value

/**
 * Окончательно разделяет выражение на выскороуровневые части [Member]
 */
class MemberConverter : ExpressionExtractor {

    override fun iterator(value: String): Iterator<Member> {
        return MemberIterator(
                MultiOperatorExtractor(
                        ExpressionPartExtractor(value)
                )
        )
    }

    private class MemberIterator(private val mIntervals: Iterator<Interval>) : Iterator<Member> {

        private var mPrev: Interval? = null

        @Throws(IllegalArgumentException::class)
        override fun next(): Member {
            val now = mIntervals.next()
            val res = if (now.operand()) {
                val lastSeparator = now.value[now.value.length - 1] == '.'
                Value(now.value, lastSeparator) //

            } else {
                val token = now.value
                if (token == "-" && (mPrev == null || !mPrev!!.operand())) {
                    Operators.UNARY_MINUS //
                } else {
                    Operators.find(token) //
                }
            }
            mPrev = now
            return res
        }

        override fun hasNext(): Boolean = mIntervals.hasNext()
    }
}
