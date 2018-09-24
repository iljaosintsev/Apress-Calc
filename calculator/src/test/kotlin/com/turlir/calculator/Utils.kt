package com.turlir.calculator

import com.turlir.calculator.converter.Member
import com.turlir.calculator.converter.Visual
import com.turlir.calculator.interpreter.NotationInterpreter
import com.turlir.calculator.member.Operator
import com.turlir.calculator.member.Operators


internal val FOS = Operators.OS

internal val FCS = Operators.CS

internal val FPLUS = StubOperator("+", 2)

internal val FMINUS = StubOperator("-", 2)

internal val FMULTI = StubOperator("*", 3)

internal val FDIV = StubOperator("/", 3)

internal val FUNARY = StubOperator("-", 4)

internal fun seq(vararg member: Member) = listOf(*member).iterator()

internal class StubOperator constructor(token: String, priority: Int) : Operator(token, priority) {

    override fun process(interpreter: NotationInterpreter) {
        throw IllegalStateException()
    }

    override fun view(): Visual {
        throw IllegalStateException()
    }
}
