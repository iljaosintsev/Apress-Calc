package com.turlir.calculator.converter;

import com.turlir.calculator.extractors.CommonTest;
import com.turlir.calculator.extractors.Interval;
import com.turlir.calculator.extractors.IntervalExtractor;
import com.turlir.calculator.member.Operators;
import com.turlir.calculator.member.Value;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Iterator;

public class MemberConverterTest extends CommonTest {

    @Test
    public void testAllMembers() {
        Iterator<Interval> seq = Arrays.asList(
                or("+"),
                or("-"),
                or("*"),
                or("/"),
                or("("),
                or(")"),
                and("3"),
                or("-")
        ).iterator();
        IntervalExtractor mock = Mockito.mock(IntervalExtractor.class);
        Mockito.when(mock.iterator(Mockito.anyString())).thenReturn(seq);
        Iterator<Member> conv = new MemberConverter(mock).iterator("");
        or(conv, Operators.PLUS);
        or(conv, Operators.UNARY_MINUS);
        or(conv, Operators.MULTI);
        or(conv, Operators.DIV);
        or(conv, Operators.OS);
        or(conv, Operators.CS);
        and(conv, new Value(3.0));
        or(conv, Operators.MINUS);
        org.junit.Assert.assertFalse(conv.hasNext());
    }

    @Test
    public void unaryMinus() {
        Iterator<Interval> seq = Arrays.asList(
                or("-"),
                and("4"),
                or("+"),
                and("2")
        ).iterator();
        IntervalExtractor mock = Mockito.mock(IntervalExtractor.class);
        Mockito.when(mock.iterator(Mockito.anyString())).thenReturn(seq);
        Iterator<Member> conv = new MemberConverter(mock).iterator("");
        or(conv, Operators.UNARY_MINUS);
        and(conv, new Value(4));
        or(conv, Operators.PLUS);
        and(conv, new Value(2));
        org.junit.Assert.assertFalse(conv.hasNext());
    }

    @Test
    public void unaryMinusInsideExp() {
        Iterator<Interval> seq = Arrays.asList(
                and("4"),
                or("+"),
                and("2"),
                or("*"),
                or("-"),
                and("1")
        ).iterator();
        IntervalExtractor mock = Mockito.mock(IntervalExtractor.class);
        Mockito.when(mock.iterator(Mockito.anyString())).thenReturn(seq);
        Iterator<Member> conv = new MemberConverter(mock).iterator("");
        and(conv, new Value(4));
        or(conv, Operators.PLUS);
        and(conv, new Value(2));
        or(conv, Operators.MULTI);
        or(conv, Operators.UNARY_MINUS);
        and(conv, new Value(1));
        org.junit.Assert.assertFalse(conv.hasNext());
    }

}