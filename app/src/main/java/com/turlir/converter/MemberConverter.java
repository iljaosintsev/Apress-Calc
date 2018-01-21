package com.turlir.converter;

import android.support.annotation.Nullable;

import com.turlir.extractors.Interval;

import java.util.Iterator;

public class MemberConverter implements Iterator<Member> {

    private final Iterator<Interval> mParent;
    @Nullable
    private Interval mPrev;

    public MemberConverter(Iterator<Interval> parent) {
        mParent = parent;
    }

    @Override
    public boolean hasNext() {
        return mParent.hasNext();
    }

    @Override
    public Member next() {
        Interval now = mParent.next();
        Member res;
        if (now.operand()) {
            res = new Value(Double.parseDouble(now.value));
        } else {
            String token = now.value;
            if (token.equals("-") && (mPrev == null || !mPrev.operand())) {
                res = Parts.UNARY_MINUS;
            } else {
                res = Parts.find(token);
            }
        }
        mPrev = now;
        return res;
    }
}
