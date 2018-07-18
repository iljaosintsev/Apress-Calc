package com.turlir.calculator.extractors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Доразделяет выражение на операторы (синус, консинус, тангенс, умножить, разделить, сложить, вычесть) и операнды
 * <br>
 * В процессе работы интератора при ошибке разрабока может быть вызвано {@link IllegalArgumentException}
 */
public class MultiOperatorExtractor implements IntervalExtractor {

    private final IntervalExtractor mParent;

    /**
     *
     * @param parent делегат – алгоритм упрощенного разделения выражения
     */
    public MultiOperatorExtractor(ExpressionPartExtractor parent) {
        mParent = parent;
    }

    @Override
    public Iterator<Interval> iterator(String value) {
        return new MultiOperatorIterator(mParent.iterator(value));
    }

    private static class MultiOperatorIterator implements Iterator<Interval> {

        private static final Pattern OPERATORS = Pattern.compile("sin\\(|cos\\(|tag\\(|[(]|[)]|\\*|-|/|\\+");
        private final Iterator<Interval> mParent;

        private Queue<Interval> mSplit;

        private MultiOperatorIterator(Iterator<Interval> iterator) {
            mParent = iterator;
            mSplit = new LinkedList<>();
        }

        @Override
        public Interval next() throws IllegalArgumentException {
            if (!mSplit.isEmpty()) {
                return mSplit.poll();
            }
            Interval i = mParent.next();
            if (!i.operand()) {
                if (i.value.length() > 1) {
                    mSplit = split(i.value);
                    return mSplit.poll();
                }
            }
            return i;
        }

        @Override
        public boolean hasNext() {
            return mParent.hasNext() || !mSplit.isEmpty();
        }

        private Queue<Interval> split(String o) {
            Matcher matcher = OPERATORS.matcher(o);
            if (matcher.find()) {
                Queue<Interval> arr = new LinkedList<>();
                do {
                    arr.add(new Interval(false, matcher.group()));
                } while (matcher.find());
                return arr;
            } else {
                throw new IllegalArgumentException("неизвестный оператор " + o);
            }
        }
    }
}
