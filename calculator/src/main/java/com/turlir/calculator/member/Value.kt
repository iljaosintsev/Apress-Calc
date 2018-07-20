package com.turlir.calculator.member

import com.turlir.calculator.converter.Member
import com.turlir.calculator.converter.Visual
import com.turlir.calculator.interpreter.NotationInterpreter

import java.math.BigDecimal
import java.math.MathContext

class Value : Member {

    private val mValue: BigDecimal
    private val isFloat: Boolean

    constructor(value: Double) {
        mValue = BigDecimal(value, MathContext.UNLIMITED)
        isFloat = false
    }

    constructor(value: String, contains: Boolean) {
        mValue = BigDecimal(value, MathContext.UNLIMITED)
        isFloat = contains
    }

    override fun operand() = true

    override fun view(): Visual = ValuePrint(mValue, isFloat)

    override fun process(interpreter: NotationInterpreter) {
        interpreter.pushDigit(mValue)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val value = other as Value?

        return value!!.mValue.compareTo(mValue) == 0
    }

    override fun hashCode(): Int {
        val temp = java.lang.Double.doubleToLongBits(mValue.toDouble())
        return (temp xor temp.ushr(32)).toInt()
    }

}
