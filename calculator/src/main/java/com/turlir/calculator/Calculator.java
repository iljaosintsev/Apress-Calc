package com.turlir.calculator;


import com.turlir.calculator.converter.Member;
import com.turlir.calculator.interpreter.NotationInterpreter;
import com.turlir.calculator.translator.NotationTranslator;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Организует взаимодействие между {@link NotationTranslator} и {@link NotationInterpreter}
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
     * @throws Exception в случае ошибки трансляции
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

    /**
     *
     * @return токены выражения в порядке вычисления
     */
    public Queue<Member> translated() {
        return mQueue;
    }

    /**
     *
     * @return количество токенов в выражении
     */
    public int size() {
        return mSize;
    }

}
