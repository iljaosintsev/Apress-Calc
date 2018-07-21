package com.turlir.calculator.member

import com.turlir.calculator.converter.Member
import com.turlir.calculator.converter.Visual

abstract class Operator protected constructor(token: String, private val mPriority: Int) : Member, Comparable<Operator> {

    private val mVisual: Visual = OperatorPrint(token)

    override fun compareTo(other: Operator) = mPriority - other.mPriority

    override fun operand() = false

    override fun view() = mVisual
}
