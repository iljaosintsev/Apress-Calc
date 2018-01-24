package com.turlir.extractors;

import java.util.Iterator;

public class ExpressionPartExtractor implements IntervalExtractor {

    @Override
    public Iterator<Interval> iterator(String value) {
        return new IntervalIterator(value);
    }

    private static class IntervalIterator implements Iterator<Interval> {

        private final String mStr;
        private int mLength;

        private IntervalIterator(String str) {
            mStr = str.replaceAll(" ", "");
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
