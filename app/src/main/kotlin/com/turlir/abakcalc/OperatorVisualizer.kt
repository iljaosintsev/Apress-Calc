package com.turlir.abakcalc

import com.turlir.calculator.converter.Printer
import com.turlir.calculator.converter.Visual

class OperatorVisualizer constructor(private val mDelegate: Visual) : CalculatorVisual {

    private var mStart: Int = 0
    private var mEnd: Int = 0
    private var mTokenLength: Int = 0

    override fun print(chain: Printer) {
        mStart = chain.length()
        mDelegate.print(chain)
        mEnd = chain.length()
        mTokenLength = mEnd - mStart
    }

    override fun selectionConstraint(selStart: Int, selEnd: Int, length: Int): Boolean {
        val safety = mTokenLength > 1 && length >= mEnd
        val union =
                if (selStart != selEnd) {
                    val pre = selStart <= mStart && selEnd < mEnd && selEnd > mStart
                    pre || selEnd >= mEnd && selStart > mStart && selStart < mEnd
                } else {
                    strictBelong(selStart, mStart, mEnd) || strictBelong(selEnd, mStart, mEnd)
                }
        return safety && union
    }

    override fun interceptSelection(nowS: Int, nowE: Int): IntArray {
        return if (nowS != nowE) {
            when {
                belong(nowE, mStart, mEnd) -> intArrayOf(nowS, mStart)
                belong(nowS, mStart, mEnd) -> intArrayOf(mEnd, nowE)
                else -> throw IllegalArgumentException() // hot
            }
        } else {
            if (nowS > mStart) {
                intArrayOf(mEnd, mEnd)
            } else {
                intArrayOf(mStart, mStart)
            }
        }
    }

    override fun constraintStart() = mStart

    override fun constraintEnd() = mEnd

    override fun toString() = mDelegate.toString()

    private fun belong(param: Int, start: Int, end: Int) = param in (start + 1)..(end - 1)

    private fun strictBelong(param: Int, start: Int, end: Int) = param in start..end

}
