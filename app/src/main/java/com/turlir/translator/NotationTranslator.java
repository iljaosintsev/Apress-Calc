package com.turlir.translator;

import com.turlir.converter.Member;

import java.util.Iterator;
import java.util.Queue;

/**
 * Сортирует части выражения в соответствии со своей нотацией
 */
public interface NotationTranslator {

    Queue<Member> translate(Iterator<Member> parent);

}
