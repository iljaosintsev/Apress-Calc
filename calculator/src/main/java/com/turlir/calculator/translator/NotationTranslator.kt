package com.turlir.calculator.translator

import com.turlir.calculator.converter.Member
import java.util.*

/**
 * Сортирует части выражения в соответствии со своей нотацией
 */
interface NotationTranslator {

    /**
     *
     * @param parent математическое выражение
     * @return очередь из токенов того же выражения в другой нотации
     */
    @Throws(Exception::class)
    fun translate(parent: Iterator<Member>): Queue<Member>

}
