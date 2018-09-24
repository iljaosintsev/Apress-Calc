package com.turlir.calculator.converter

/**
 * Прямая реализация связного списка {@link Member}
 */
class Expression
    internal constructor (source: Iterator<Member>) : MathExpression {

    private val items: List<Member>
    private var index: Int = 0

    init {
        val data = mutableListOf<Member>()
        while (source.hasNext()) {
            data.add(source.next())
        }
        items = ArrayList(data)
    }

    override val value: Member
        get() {
            return items[index++]
        }

    override fun inline(): List<Member> {
        return items
    }

    override fun isLast() = index == items.size

}