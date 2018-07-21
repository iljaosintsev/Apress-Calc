package com.turlir.abakcalc

import com.turlir.calculator.converter.Visual

interface CalculatorVisual : Visual {

    fun selectionConstraint(selStart: Int, selEnd: Int, length: Int): Boolean

    fun interceptSelection(nowS: Int, nowE: Int): IntArray

    fun constraintStart(): Int

    fun constraintEnd(): Int
}
