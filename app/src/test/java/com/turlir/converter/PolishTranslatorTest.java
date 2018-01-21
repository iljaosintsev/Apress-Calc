package com.turlir.converter;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PolishTranslatorTest {

    @Test
    public void demo() {
        Iterator<Member> sequence = Arrays.asList(new Value(23.54), Parts.PLUS,  new Value(45.67)).iterator();
        PolishTranslator sor = new PolishTranslator();
        Queue<Member> lst = sor.translate(sequence);
        assertNotNull(lst);
        assertEquals(3, lst.size());
        assertThat(lst, CoreMatchers.hasItems(new Value(23.54), new Value(45.67), Parts.PLUS));
    }

}