package com.turlir.converter;

import com.turlir.extractors.Interval;

import java.util.Iterator;

public class MemberConverter implements Iterator<Member> {

    private final Iterator<Interval> mParent;

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
        if (now.operand()) {
            return new Value(Double.parseDouble(now.value));
        } else {
            String token = now.value;
            return Parts.find(token);
        }
    }
}
