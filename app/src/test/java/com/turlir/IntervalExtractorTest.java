package com.turlir;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalExtractorTest {

    @Test
    public void simpleExtractTest() {
        Iterator<Interval> extractor = new IntervalExtractor("23.54+45.67");

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("23.54"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("+"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("45.67"));

        assertFalse(extractor.hasNext());
    }

    @Test
    public void extractComplexOperatorTest() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new FastIntervalExtractor("5*sin(2)") // IntervalExtractor
        );

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("5"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("*"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("sin("));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("2"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or(")"));

        assertFalse(extractor.hasNext());
    }

    @Test
    public void bracketExtractTest() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new ExpressionPartExtractor("-2*(3+(16-9))")
        );

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("-"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("2"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("*"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("("));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("3"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("+"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("("));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("16"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or("-"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), and("9"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or(")"));

        assertTrue(extractor.hasNext());
        assertEquals(extractor.next(), or(")"));

        assertFalse(extractor.hasNext());
    }

    private static Interval or(String value) {
        return new Interval(false, value);
    }

    private static Interval and(String value) {
        return new Interval(true, value);
    }

}