package com.turlir.calculator.converter

import java.util.*

class Expression(val value: Member) {

    var next: Expression? = null
        private set(value) {
            field = value
        }

    internal fun next(value: Member): Expression {
        val next = Expression(value)
        this.next = next
        return next
    }

    fun inline(): List<Member> {
        val copy = LinkedList<Member>()
        var now: Expression = this
        while (true) {
            copy.add(now.value)
            now = if (now.isLast()) {
                break
            } else {
                now.next!!
            }
        }
        return copy
    }

    fun isLast() = next == null

}