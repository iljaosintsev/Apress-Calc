package com.turlir;


import com.turlir.converter.Member;
import com.turlir.converter.ExpressionExtractor;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.translator.NotationTranslator;

import java.util.Queue;

/**
 * Простой класс для организации взаимодействия между {@link NotationTranslator} и {@link NotationInterpreter}
 */
public class Calculator {

    private final ExpressionExtractor mConverter;
    private final NotationTranslator mTranslator;
    private final NotationInterpreter mInter;

    public Calculator(ExpressionExtractor converter, NotationTranslator translator, NotationInterpreter inter) {
        mConverter = converter;
        mTranslator = translator;
        mInter = inter;
    }

    /**
     * Вычисляет значение математического выражения
     * @param exp выражение
     * @return значение
     * @throws RuntimeException в случае ошибки разбора или интерпретации выражения
     */
    public Double calc(String exp) throws RuntimeException {
        Queue<Member> queue = mTranslator.translate(
                mConverter.iterator(exp)
        );
        for (Member current : queue) {
            current.process(mInter);
        }
        return mInter.poolDigit();
    }

}
