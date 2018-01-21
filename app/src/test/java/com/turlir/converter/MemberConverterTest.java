package com.turlir.converter;

import com.turlir.extractors.CommonTest;
import com.turlir.extractors.Interval;

import org.junit.Test;

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
                and("3")
        ).iterator();
        Iterator<Member> conv = new MemberConverter(seq);
        or(conv, Parts.PLUS);
        or(conv, Parts.MINUS);
        or(conv, Parts.MULTI);
        or(conv, Parts.DIV);
        or(conv, Parts.OS);
        or(conv, Parts.CS);
        and(conv, new Value(3.0));
        org.junit.Assert.assertFalse(conv.hasNext());
    }

}