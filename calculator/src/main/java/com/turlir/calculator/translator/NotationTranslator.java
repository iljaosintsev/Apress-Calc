package com.turlir.calculator.translator;

import com.turlir.calculator.converter.Member;

import java.util.Iterator;
import java.util.Queue;

/**
 * Сортирует части выражения в соответствии со своей нотацией
 */
public interface NotationTranslator {

    /**
     *
     * @param parent математическое выражение
     * @return очередь из токенов того же выражения в другой нотации
     */
    Queue<Member> translate(Iterator<Member> parent) throws Exception;

}
