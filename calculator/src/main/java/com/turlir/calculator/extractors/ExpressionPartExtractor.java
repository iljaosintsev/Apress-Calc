package com.turlir.calculator.extractors;

import java.util.Iterator;

/**
 * Простое выделение из математического выражения операторов и операндов <br>
 * Выполняет чистку строкового представления выражения
 */
public class ExpressionPartExtractor implements IntervalExtractor {

    @Override
    public Iterator<Interval> iterator(String value) {
        return new IntervalIterator(value);
    }

    private static class IntervalIterator implements Iterator<Interval> {

        private final String mStr;
        private int mLength;

        private IntervalIterator(String str) {
            mStr = str.replaceAll("\\s+", "").replaceAll(",", ".");
        }

        @Override
        public Interval next() {
            Interval part = new Interval(mStr, mLength);
            mLength += part.value.length();
            return part;
        }

        @Override
        public boolean hasNext() {
            return mLength < mStr.length();
        }

    }
}
