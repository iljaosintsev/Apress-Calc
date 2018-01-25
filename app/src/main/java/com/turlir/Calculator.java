package com.turlir;


import com.turlir.converter.ExpressionExtractor;
import com.turlir.converter.Member;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.translator.NotationTranslator;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Queue;

/**
 * Организует взаимодействие между  {@link ExpressionExtractor}, {@link NotationTranslator}
 * и {@link NotationInterpreter}
 */
public class Calculator {

    private final ExpressionExtractor mConverter;
    private final NotationTranslator mTranslator;
    private final NotationInterpreter mInter;

    private final Box mBox;

    public Calculator(ExpressionExtractor converter, NotationTranslator translator, NotationInterpreter inter) {
        mConverter = converter;
        mTranslator = translator;
        mInter = inter;

        mBox = new Box();
    }

    /**
     * Вычисляет значение математического выражения. Гарантирует наличие результата
     * @param exp выражение
     * @return абстракция с результатом расчета выражения
     * @throws RuntimeException в случае ошибки разбора или интерпретации выражения
     */
    public Box calc(String exp) throws Exception {
        Iterator<Member> iterator = mConverter.iterator(exp);
        Queue<Member> queue = mTranslator.translate(iterator);
        for (Member current : queue) {
            current.process(mInter);
        }
        double a = mInter.poolDigit();
        return mBox.box(a);
    }

    public static class Box {

        private static final DecimalFormat DF = new DecimalFormat("#.###"); // формат результата

        double value;

        public String print() {
            return DF.format(value);
        }

        private Box box(double value) {
            this.value = value;
            return this;
        }
    }

}
