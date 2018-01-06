package com.turlir;

import java.util.Iterator;

public class FastIntervalExtractor implements Iterator<Interval> {

    private final String str;
    private final int length;
    private Range r;

    FastIntervalExtractor(final String str) {
        this.str = str.trim();
        this.length = str.length();
        this.r = new Range(0, 0);
    }

    @Override
    public Interval next() {
        boolean l;
        char c = str.charAt(r.index);
        boolean f = digit(c);
        if (!f && operator(c)) {
            Interval i = new Interval(false, r.nextSubstring(str));
            r = r.cycle();
            return i;
        }
        do {
            r.captureNext();
            if (r.exact(length)) {
                c = str.charAt(r.sum());
                l = digit(c);
            } else {
                break;
            }
        } while (f == l);
        Interval i = new Interval(f, r.directSubstring(str));
        r = r.cycle();
        return i;
    }

    @Override
    public boolean hasNext() {
        return r.exactWithoutOffset(length);
    }

    private static boolean digit(char c) {
        return Character.isDigit(c) || c == '.' /*|| c == ','*/;
    }

    private static boolean operator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

}
