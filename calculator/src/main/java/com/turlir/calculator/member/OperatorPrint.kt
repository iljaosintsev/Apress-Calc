package com.turlir.calculator.member


import com.turlir.calculator.converter.Printer
import com.turlir.calculator.converter.Visual

internal class OperatorPrint(private val mToken: String) : Visual {

    override fun print(chain: Printer) {
        chain.append(mToken)
    }

    override fun toString(): String {
        return mToken
    }
}
