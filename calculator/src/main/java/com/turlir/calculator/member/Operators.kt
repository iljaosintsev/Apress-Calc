package com.turlir.calculator.member

import com.turlir.calculator.interpreter.NotationInterpreter
import java.math.BigDecimal
import java.math.MathContext

abstract class Operators {

    companion object {

        val OS = object : Operator("(", 1) {

            override fun process(interpreter: NotationInterpreter) {
                //
            }
        }

        val CS = object : Operator(")", 1) {

            override fun process(interpreter: NotationInterpreter) {
                //
            }
        }

        val PLUS = object : Operator(" + ", 2) {

            override fun process(interpreter: NotationInterpreter) {
                val a = interpreter.poolDigit()
                val b = interpreter.poolDigit()
                interpreter.pushDigit(a.add(b))
            }
        }

        val MINUS = object : Operator(" - ", 2) {

            override fun process(interpreter: NotationInterpreter) {
                val a = interpreter.poolDigit()
                val b = interpreter.poolDigit()
                interpreter.pushDigit(b.subtract(a))
            }
        }

        val MULTI = object : Operator(" * ", 3) {

            override fun process(interpreter: NotationInterpreter) {
                val a = interpreter.poolDigit()
                val b = interpreter.poolDigit()
                interpreter.pushDigit(a.multiply(b))
            }
        }

        val DIV = object : Operator(" / ", 3) {

            override fun process(interpreter: NotationInterpreter) {
                val a = interpreter.poolDigit()
                val b = interpreter.poolDigit()
                interpreter.pushDigit(b.divide(a, MathContext.DECIMAL64))
            }
        }

        val UNARY_MINUS = object : Operator("-", 4) {

            private val MULTIPLICAND = BigDecimal(-1)

            override fun process(interpreter: NotationInterpreter) {
                val a = interpreter.poolDigit()
                interpreter.pushDigit(a.multiply(MULTIPLICAND))
            }
        }

        fun find(token: String) = when (token) {
            "*" -> MULTI
            "/" -> DIV
            "+" -> PLUS
            "-" -> MINUS
            "u-" -> UNARY_MINUS
            "(" -> OS
            ")" -> CS
            else -> throw IllegalArgumentException("Операция не доступна")
        }

    }

}
