package com.turlir.calculator.member

import com.turlir.calculator.converter.Printer
import com.turlir.calculator.converter.Visual

import java.math.BigDecimal

internal class ValuePrint(private val mValue: BigDecimal, private val isFloat: Boolean) : Visual {

    override fun print(chain: Printer) {
        chain.append(mValue)
        if (isFloat) chain.appendSeparator()
    }

    override fun toString() = mValue.toString()
}
