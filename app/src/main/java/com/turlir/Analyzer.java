package com.turlir;

import com.turlir.converter.ExpressionExtractor;
import com.turlir.converter.Member;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Анализирует математическое выражение. Выражение может быть не полным, но должно содержать только известные
 * операторы и {@link Double} операнды
 */
public class Analyzer {

    private final ExpressionExtractor mConverter;

    public Analyzer(ExpressionExtractor converter) {
        mConverter = converter;
    }

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return разобранное на токены выражение
     * @throws RuntimeException в случае ошибки разбора
     */
    public Iterator<Member> analyze(String exp) throws Exception {
        return mConverter.iterator(exp);
    }

    /**
     * Анализирует математическое выражение
     * @param exp выражение
     * @return разобранное на токены выражение
     * @throws RuntimeException в случае ошибки разбора
     */
    public List<Member> slice(String exp) throws Exception {
        Iterator<Member> primaryQ = analyze(exp);
        List<Member> copy = new LinkedList<>();
        while (primaryQ.hasNext()) {
            Member item = primaryQ.next();
            copy.add(item);
        }
        return copy;
    }

}
