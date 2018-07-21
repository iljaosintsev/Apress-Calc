package com.turlir.abakcalc

import com.turlir.calculator.converter.Printer
import com.turlir.calculator.converter.Visual

class ValueVisualizer constructor(private val mDelegate: Visual) : CalculatorVisual {

    override fun print(chain: Printer) {
        mDelegate.print(chain)
    }

    override fun selectionConstraint(selStart: Int, selEnd: Int, length: Int) = false

    override fun interceptSelection(nowS: Int, nowE: Int) = IntArray(0) // []

    override fun constraintStart() = 0

    override fun constraintEnd() = 0

    override fun toString() = mDelegate.toString()
}
