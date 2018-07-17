package com.turlir.calculator.converter;

import com.turlir.calculator.extractors.Interval;
import com.turlir.calculator.extractors.IntervalExtractor;

import java.util.Iterator;

public class MemberConverter implements ExpressionExtractor {

    private final IntervalExtractor mExtractor;

    public MemberConverter(IntervalExtractor parent) {
        mExtractor = parent;
    }

    @Override
    public Iterator<Member> iterator(String value) {
        return new MemberIterator(mExtractor.iterator(value));
    }

    private static class MemberIterator implements Iterator<Member> {

        private final Iterator<Interval> mIntervals;

        // @Nullable
        private Interval mPrev;

        private MemberIterator(Iterator<Interval> intervals) {
            mIntervals = intervals;
        }

        @Override
        public boolean hasNext() {
            return mIntervals.hasNext();
        }

        @Override
        public Member next() {
            Interval now = mIntervals.next();
            Member res;
            if (now.operand()) {
                boolean lastSeparator = now.value.charAt(now.value.length() - 1) == '.';
                res = new Value(now.value, lastSeparator);
            } else {
                String token = now.value;
                if (token.equals("-") && (mPrev == null || !mPrev.operand())) {
                    res = Operators.UNARY_MINUS;
                } else {
                    res = Operators.find(token);
                }
            }
            mPrev = now;
            return res;
        }

    }
}