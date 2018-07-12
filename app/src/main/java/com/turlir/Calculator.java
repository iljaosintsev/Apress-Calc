package com.turlir;


import com.turlir.converter.Member;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.translator.NotationTranslator;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Организует взаимодействие между  {@link NotationTranslator} и {@link NotationInterpreter}
 */
public class Calculator {

    private final NotationTranslator mTranslator;
    private final NotationInterpreter mInter;

    private Queue<Member> mQueue;
    private int mSize;

    public Calculator(NotationTranslator translator, NotationInterpreter inter) {
        mTranslator = translator;
        mInter = inter;
        mQueue = new LinkedList<>();
    }

    /**
     * Вычисляет значение математического выражения
     * @return результата вычисления
     * @throws RuntimeException в случае ошибки интерпретации
     */
    public BigDecimal calc(Iterator<Member> sequence) throws Exception {
        mQueue = mTranslator.translate(sequence);
        mSize = 0;
        for (Member current : mQueue) {
            current.process(mInter);
            mSize++;
        }
        return mInter.poolDigit();
    }

    public Queue<Member> translated() {
        return mQueue;
    }

    public int size() {
        return mSize;
    }

}
