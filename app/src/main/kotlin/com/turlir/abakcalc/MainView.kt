package com.turlir.abakcalc

import android.content.Context

import com.turlir.calculator.converter.Visual

interface MainView {

    fun showResult(digit: String)

    fun showError(error: String)

    val context: Context

    fun setRepresentation(v: List<CalculatorVisual>)

    fun setNotation(v: List<Visual>)

    fun resetToEmpty()
}
