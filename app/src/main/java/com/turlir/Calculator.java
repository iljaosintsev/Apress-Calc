package com.turlir;


import android.support.v4.util.Pair;

import com.turlir.converter.ExpressionExtractor;
import com.turlir.converter.Member;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.translator.NotationTranslator;

import java.text.DecimalFormat;
import java.util.Queue;

/**
 * Организует взаимодействие между  {@link ExpressionExtractor}, {@link NotationTranslator}
 * и {@link NotationInterpreter}
 */
public class Calculator {

    private static final DecimalFormat DF = new DecimalFormat("#.###"); // формат результата

    private final ExpressionExtractor mConverter;
    private final NotationTranslator mTranslator;
    private final NotationInterpreter mInter;

    private final StringBuilder mPrinter;

    public Calculator(ExpressionExtractor converter, NotationTranslator translator, NotationInterpreter inter) {
        mConverter = converter;
        mTranslator = translator;
        mInter = inter;

        mPrinter = new StringBuilder();
    }

    /**
     * Анализирует математическое выражение. Предоставляет результат анализа и его строковое представление
     * @param exp выражение
     * @return пара, строковое представление - разобранное выражение
     * @throws RuntimeException в случае ошибки разбора
     */
    public Pair<String, Queue<Member>> analyze(String exp) throws Exception {
        mPrinter.delete(0, mPrinter.length());
        Queue<Member> natural = mConverter.iterator(exp);
        for (Member current : natural) {
            current.print(mPrinter);
        }
        return new Pair<>(mPrinter.toString(), natural);
    }

    /**
     * Вычисляет значение математического выражения
     * @param natural результат анализв функцией {@link #analyze(String)} выражения
     * @return строковое представления числа - результата выражения
     * @throws RuntimeException в случае ошибки интерпретации
     */
    public String calc(Queue<Member> natural) throws Exception {
        Queue<Member> queue = mTranslator.translate(natural);
        for (Member current : queue) {
            current.process(mInter);
        }
        return DF.format(mInter.poolDigit());
    }

    double calc(String exp) throws Exception {
        Queue<Member> queue = mTranslator.translate(analyze(exp).second);
        for (Member current : queue) {
            current.process(mInter);
        }
        return mInter.poolDigit();
    }

}
