package com.turlir.extractors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiOperatorExtractor implements Iterator<Interval> {

    private final Pattern mOperators = Pattern.compile("sin\\(|cos\\(|tag\\(|[(]|[)]");
    private final Iterator<Interval> mParent;

    private Queue<Interval> mSplit;

    public MultiOperatorExtractor(Iterator<Interval> parent) {
        mParent = parent;
        mSplit = new LinkedList<>();
    }

    @Override
    public Interval next() {
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
        Matcher matcher = mOperators.matcher(o);
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
