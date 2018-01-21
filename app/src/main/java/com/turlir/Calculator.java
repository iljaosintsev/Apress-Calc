package com.turlir;


import com.turlir.interpreter.NotationInterpreter;
import com.turlir.converter.Member;
import com.turlir.converter.MemberConverter;
import com.turlir.translator.NotationTranslator;
import com.turlir.extractors.ExpressionPartExtractor;
import com.turlir.extractors.MultiOperatorExtractor;

import java.util.Queue;

/**
 * Простой класс для организации взаимодействия между {@link NotationTranslator} и {@link NotationInterpreter}
 */
public class Calculator {

    private final NotationTranslator mConverter;
    private final NotationInterpreter mInter;

    public Calculator(NotationTranslator translator, NotationInterpreter inter) {
        mConverter = translator;
        mInter = inter;
    }

    /**
     * Вычисляет значение математического выражения
     * @param exp выражение
     * @return значение
     * @throws RuntimeException в случае ошибки разбора или интерпретации выражения
     */
    public Double calc(String exp) throws RuntimeException {
        Queue<Member> queue = mConverter.translate(
                new MemberConverter(
                        new MultiOperatorExtractor(
                                new ExpressionPartExtractor(exp)
                        )
                )
        );
        for (Member current : queue) {
            current.process(mInter);
        }
        return mInter.poolDigit();
    }

}
