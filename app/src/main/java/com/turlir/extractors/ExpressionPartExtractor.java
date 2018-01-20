package com.turlir.extractors;

import java.util.Iterator;

public class ExpressionPartExtractor implements Iterator<Interval> {

    private final String str;
    private int length;

    ExpressionPartExtractor(final String str) {
        this.str = str.trim();
        length = 0;
    }

    @Override
    public Interval next() {
        Interval part = new Interval(str, length);
        length += part.value.length();
        return part;
    }

    @Override
    public boolean hasNext() {
        return length < str.length();
    }
}
