package com.turlir.calculator.converter

import java.util.*

/**
 * Прямая реализация связного списка {@link Member}
 */
class Expression
    internal constructor (override val value: Member) : MathExpression {

    override var next: MathExpression? = null
        private set(value) { field = value }

    /**
     * Создает контейнер для получения очередного токена
     */
    internal fun next(value: Member): Expression {
        val next = Expression(value)
        this.next = next
        return next
    }

    override fun inline(): List<Member> {
        val copy = LinkedList<Member>()
        var now: MathExpression = this
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

    override fun isLast() = next == null

}