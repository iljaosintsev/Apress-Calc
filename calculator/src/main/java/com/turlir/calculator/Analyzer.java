package com.turlir.calculator;

import com.turlir.calculator.converter.ExpressionExtractor;
import com.turlir.calculator.converter.Member;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Анализирует математическое выражение. Выражение может быть не полным, но должно содержать только известные
 * операторы и {@link Double} операнды, представленные в виде {@link Member}
 */
public class Analyzer {

    private final ExpressionExtractor mConverter;

    public Analyzer(ExpressionExtractor converter) {
        mConverter = converter;
    }

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return последовательность токенов выражения
     * @throws IllegalArgumentException при работе итератора в случае ошибки разбора
     */
    public Iterator<Member> analyze(String exp) {
        return mConverter.iterator(exp);
    }

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return список из токенов выражения
     * @throws IllegalArgumentException в случае ошибки разбора
     */
    public List<Member> slice(String exp) throws IllegalArgumentException {
        Iterator<Member> primaryQ = analyze(exp);
        List<Member> copy = new LinkedList<>();
        while (primaryQ.hasNext()) {
            Member item = primaryQ.next();
            copy.add(item);
        }
        return copy;
    }

}
