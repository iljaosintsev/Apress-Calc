package com.turlir.calculator.converter

interface MathExpression {

    val value: Member

    val next: MathExpression?

    fun inline(): List<Member>

    fun isLast(): Boolean

}