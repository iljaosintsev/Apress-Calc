package com.turlir.extractors;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertFalse;

public class IntervalExtractorTest extends CommonTest {

    @Test
    public void simpleExtractTest() {
        Iterator<Interval> extractor = new IntervalExtractor("23.54+45.67");

        and(extractor, "23.54");

        or(extractor, "+");

        and(extractor, "45.67");

        assertFalse(extractor.hasNext());
    }

    @Test
    public void extractComplexOperatorTest() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new FastIntervalExtractor("5*sin(2+cos(6))") // IntervalExtractor
        );

        and(extractor, "5");

        or(extractor, "*");

        or(extractor, "sin(");

        and(extractor, "2");

        or(extractor, "+");

        or(extractor, "cos(");

        and(extractor, "6");

        or(extractor, ")");

        or(extractor, ")");

        assertFalse(extractor.hasNext());
    }

    @Test
    public void bracketExtractTest() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new ExpressionPartExtractor("-2*(3+(16-9))")
        );

        or(extractor, "-");

        and(extractor, "2");

        or(extractor, "*");

        or(extractor, "(");

        and(extractor, "3");

        or(extractor, "+");

        or(extractor, "(");

        and(extractor, "16");

        or(extractor, "-");

        and(extractor, "9");

        or(extractor, ")");

        or(extractor, ")");

        assertFalse(extractor.hasNext());
    }

}