package com.turlir.converter;

import com.turlir.extractors.CommonTest;
import com.turlir.extractors.Interval;
import com.turlir.extractors.IntervalExtractor;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

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
        Queue<Member> conv = new MemberConverter(mock).iterator("");
        or(conv, Parts.PLUS);
        or(conv, Parts.UNARY_MINUS);
        or(conv, Parts.MULTI);
        or(conv, Parts.DIV);
        or(conv, Parts.OS);
        or(conv, Parts.CS);
        and(conv, new Value(3.0));
        or(conv, Parts.MINUS);
        org.junit.Assert.assertTrue(conv.isEmpty());
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
        Queue<Member> conv = new MemberConverter(mock).iterator("");
        or(conv, Parts.UNARY_MINUS);
        and(conv, new Value(4));
        or(conv, Parts.PLUS);
        and(conv, new Value(2));
        org.junit.Assert.assertTrue(conv.isEmpty());
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
        Queue<Member> conv = new MemberConverter(mock).iterator("");
        and(conv, new Value(4));
        or(conv, Parts.PLUS);
        and(conv, new Value(2));
        or(conv, Parts.MULTI);
        or(conv, Parts.UNARY_MINUS);
        and(conv, new Value(1));
        org.junit.Assert.assertTrue(conv.isEmpty());
    }

}