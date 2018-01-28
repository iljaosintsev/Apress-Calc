package com.turlir.extractors;

import com.turlir.converter.Member;

import java.util.Iterator;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    protected static void and(Queue<Member> it, Member value) {
        assertFalse(it.isEmpty());
        assertEquals(it.poll(), value);
    }

    protected static void or(Queue<Member> it, Member value) {
        assertFalse(it.isEmpty());
        assertTrue(it.poll() == value);
    }

    //

    protected static Interval or(String value) {
        return new Interval(false, value);
    }

    protected static Interval and(String value) {
        return new Interval(true, value);
    }
}
