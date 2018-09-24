package com.turlir.calculator.converter

import java.util.*

/**
 * Ленивое вычисление очередного токена. Итератор должен содержить минимум один элемент, поскольку получение
 * очередного токена связно с методом {@link MathExpression.next} и инициализация проводится в конструкторе
 */
class SequenceExpression
    internal constructor(private val link: Iterator<Member>) : MathExpression {

    override val value: Member
        get() {
            return link.next()
        }

    override fun inline(): List<Member> {
        val list = LinkedList<Member>()
        while (!isLast()) {
            list.add(value)
        }
        return list
    }

    override fun isLast(): Boolean {
        return !link.hasNext()
    }

}