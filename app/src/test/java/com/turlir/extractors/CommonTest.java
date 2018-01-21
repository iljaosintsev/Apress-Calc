package com.turlir.extractors;

import com.turlir.converter.Member;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class CommonTest {

    protected static void and(Iterator<Interval> it, String value) {
        assertTrue(it.hasNext());
        assertEquals(it.next(), and(value));
    }

    protected static void or(Iterator<Interval> it, String value) {
        assertTrue(it.hasNext());
        assertEquals(it.next(), or(value));
    }


    //

    protected static void and(Iterator<Member> it, Member value) {
        assertTrue(it.hasNext());
        assertEquals(it.next(), value);
    }

    protected static void or(Iterator<Member> it, Member value) {
        assertTrue(it.hasNext());
        assertTrue(it.next() == value);
    }

    //

    protected static Interval or(String value) {
        return new Interval(false, value);
    }

    protected static Interval and(String value) {
        return new Interval(true, value);
    }
}
