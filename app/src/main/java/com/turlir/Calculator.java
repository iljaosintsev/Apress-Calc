package com.turlir;


import com.turlir.converter.Member;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.translator.NotationTranslator;

import java.util.Iterator;
import java.util.Queue;

/**
 * Организует взаимодействие между  {@link NotationTranslator} и {@link NotationInterpreter}
 */
public class Calculator {

    private final NotationTranslator mTranslator;
    private final NotationInterpreter mInter;

    public Calculator(NotationTranslator translator, NotationInterpreter inter) {
        mTranslator = translator;
        mInter = inter;
    }

    /**
     * Вычисляет значение математического выражения
     * @return результата вычисления
     * @throws RuntimeException в случае ошибки интерпретации
     */
    public double calc(Iterator<Member> sequence) throws Exception {
        Queue<Member> queue = mTranslator.translate(sequence);
        for (Member current : queue) {
            current.process(mInter);
        }
        return mInter.poolDigit();
    }

}
