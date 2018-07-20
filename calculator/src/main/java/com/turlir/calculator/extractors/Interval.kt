package com.turlir.calculator.extractors


/**
 * Подстрока матетимаческого выражения, соответствующая или оператору или операнду (числу)
 */
class Interval {

    val value: String
    private val type: Boolean

    constructor(type: Boolean, value: String) {
        this.value = value
        this.type = type
    }

    /**
     * Выделение из строки {@param origin} начиная с позиции {@param index} следующего куска <br></br>
     * от оператора - до операнда и наоборот
     * @param origin математическое выражение
     * @param index начальная позиция
     */
    constructor(origin: String, index: Int) {
        val charAt = origin[index]
        val isDigit = digit(charAt)
        if (!isDigit && operator(charAt)) {
            type = false
            value = origin.substring(index, index + 1)
        } else {
            val offset = generateSequence(0) { it + 1 }
                    .takeWhile { index + it < origin.length }
                    .map { index + it }
                    .takeWhile { digit(origin[it]) == isDigit }
                    .map { it - index + 1}
                    .last()
            type = isDigit
            value = origin.substring(index, index + offset)
        }
    }

    /**
     *
     * @return является ли этот участок выражения операндом
     */
    fun operand(): Boolean = type


    private fun digit(c: Char): Boolean {
        return Character.isDigit(c) || c == '.' /*|| c == ','*/
    }

    private fun operator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Interval

        if (value != other.value || type != other.type) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 139 * result + if (type) 1 else 0
        return result
    }

    override fun toString() = "Interval{value='$value, type=$type$}"
}
