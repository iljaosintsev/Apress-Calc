package com.turlir.calculator.converter

import java.util.*

/**
 * Ленивое вычисление очередного токена. Итератор должен содержить минимум один элемент, поскольку получение
 * очередного токена связно с методом {@link MathExpression.next} и инициализация проводится в конструкторе
 */
class SequenceExpression
    internal constructor(private val link: Iterator<Member>) : MathExpression {

    private var item: Member

    init {
        item = link.next()
    }

    override val value: Member
        get() {
            return item
        }

    override var next: MathExpression? = null
        private set(value) { field = value }
        get() {
            item = link.next()
            return this
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