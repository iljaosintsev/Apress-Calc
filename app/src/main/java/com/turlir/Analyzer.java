package com.turlir;

import com.turlir.converter.ExpressionExtractor;
import com.turlir.converter.Member;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Анализирует математическое выражение. Выражение может быть не полным, но должно содержать только известные
 * операторы и {@link Double} операнды
 */
public class Analyzer {

    private final ExpressionExtractor mConverter;

    private Queue<Member> mSequence;
    private final StringBuilder mPrinter;

    public Analyzer(ExpressionExtractor converter) {
        mConverter = converter;
        mSequence = new LinkedList<>();
        mPrinter = new StringBuilder();
    }

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return разобранное на токены выражение
     * @throws RuntimeException в случае ошибки разбора
     */
    public Queue<Member> analyze(String exp) throws Exception {
        mSequence = mConverter.iterator(exp);
        return mSequence;
    }

    /**
     *
     * @return строковое представление входного математического выражения
     */
    public String print() {
        for (Member current : mSequence) {
            current.print(mPrinter);
        }
        String s = mPrinter.toString();
        mPrinter.delete(0, mPrinter.length());
        return s;
    }
}
