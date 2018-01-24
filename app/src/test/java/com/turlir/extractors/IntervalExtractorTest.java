package com.turlir.extractors;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertFalse;

public class IntervalExtractorTest extends CommonTest {

    @Test
    public void bracketExtractTest() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new ExpressionPartExtractor()
        ).iterator("-2*(3+(16-9))");

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

    @Test
    public void unaryMinus() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new ExpressionPartExtractor()
        ).iterator("-4 + 2");
        or(extractor, "-");
        and(extractor, "4");
        or(extractor, "+");
        and(extractor, "2");
    }

    @Test
    public void unaryMinusInsideExp() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new ExpressionPartExtractor()
        ).iterator("4 + 2 * -1");
        and(extractor, "4");
        or(extractor, "+");
        and(extractor, "2");
        or(extractor, "*");
        or(extractor, "-");
        and(extractor, "1");
    }

    @Test
    public void bracketMultiplyUnaryValue() {
        Iterator<Interval> extractor = new MultiOperatorExtractor(
                new ExpressionPartExtractor()
        ).iterator("( 2 + 4) * -3");
        or(extractor, "(");
        and(extractor, "2");
        or(extractor, "+");
        and(extractor, "4");
        or(extractor, ")");
        or(extractor, "*");
        or(extractor, "-");
        and(extractor, "3");
    }

}