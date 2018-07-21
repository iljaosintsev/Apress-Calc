package com.turlir.calculator.converter

import com.turlir.calculator.extractors.Interval
import com.turlir.calculator.member.Operators
import com.turlir.calculator.member.Value
import java.util.*
import java.util.regex.Pattern

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
}

internal class MemberIterator(private val mIntervals: Iterator<Interval>) : Iterator<Member> {

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


/**
 * Доразделяет выражение на операторы (синус, консинус, тангенс, умножить, разделить, сложить, вычесть) и операнды
 * <br></br>
 * В процессе работы интератора при ошибке разрабока может быть вызвано [IllegalArgumentException]
 */
internal class MultiOperatorExtractor
/**
 *
 * @param parent делегат – алгоритм упрощенного разделения выражения
 */
constructor(private val parent: Iterator<Interval>) : Iterator<Interval> {

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


/**
 * Простое выделение из математического выражения операторов и операндов <br></br>
 * Выполняет чистку строкового представления выражения
 */
internal class ExpressionPartExtractor constructor(str: String) : Iterator<Interval> {

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
