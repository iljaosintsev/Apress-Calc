package com.turlir;

import com.turlir.converter.ExpressionExtractor;
import com.turlir.converter.Member;
import com.turlir.converter.Visual;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Анализирует математическое выражение. Выражение может быть не полным, но должно содержать только известные
 * операторы и {@link Double} операнды
 */
public class Analyzer {

    private final ExpressionExtractor mConverter;

    private Queue<Member> mSequence;

    public Analyzer(ExpressionExtractor converter) {
        mConverter = converter;
        mSequence = new LinkedList<>();
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

    public List<Visual> display() {
        List<Visual> views = new ArrayList<>(mSequence.size());
        for (Member member : mSequence) {
            views.add(member.view());
        }
        return views;
    }
}
