package com.turlir;


import com.turlir.converter.Member;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.translator.NotationTranslator;

import java.text.DecimalFormat;
import java.util.Queue;

/**
 * Организует взаимодействие между  {@link NotationTranslator} и {@link NotationInterpreter}
 */
public class Calculator {

    private static final DecimalFormat DF = new DecimalFormat("#.###"); // формат результата

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
    double calc(Queue<Member> sequence) throws Exception {
        Queue<Member> queue = mTranslator.translate(sequence);
        for (Member current : queue) {
            current.process(mInter);
        }
        return mInter.poolDigit();
    }

    private String print(double value) {
        return DF.format(value);
    }

    public String represent(Queue<Member> value) throws Exception {
        return print(calc(value));
    }

}
