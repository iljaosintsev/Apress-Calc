package com.turlir.extractors;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertFalse;

public class IntervalExtractorTest extends CommonTest {

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