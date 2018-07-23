package com.turlir.calculator.converter

import java.util.*

/**
 * Ленивое вычисление очередного токена
 */
class SequenceExpression
    internal constructor(private val link: Iterator<Member>) : MathExpression {

    override val value: Member
        get() {
            return link.next()
        }

    override var next: MathExpression? = null
        private set(value) { field = value }
        get() {
            if(!isLast()) {
                return this
            } else {
                throw NoSuchElementException()
            }
        }

    override fun inline(): List<Member> {
        val list = LinkedList<Member>()
        var exp: MathExpression = this
        while (true) {
            val member = exp.value
            list.add(member)
            if (exp.isLast()) {
                break
            } else {
                exp = exp.next!!
            }
        }
        return list
    }

    override fun isLast(): Boolean {
        return !link.hasNext()
    }

}