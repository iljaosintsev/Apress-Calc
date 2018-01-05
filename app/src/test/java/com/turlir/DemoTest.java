package com.turlir;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DemoTest {

    private IDemo d;

    @Before
    public void setUp() throws Exception {
        d = new ComplexDemo(new Demo());
    }

    @Test
    public void simpleExtractTest() throws Exception {
        List<Pair<String, String>> list = d.input("23.54+45.67");
        assertEquals(
                Arrays.asList(and("23.54"), or("+"), and("45.67")),
                list
        );

        list = d.input("-21.9+3*40");
        assertEquals(
                Arrays.asList(or("-"), and("21.9"), or("+"), and("3"), or("*"), and("40")),
                list
        );
    }

    @Test
    public void bracketExtractTest() {
        List<Pair<String, String>> list = d.input("-2*(3+(16-9))");
        assertEquals(
                Arrays.asList(
                        or("-"), and("2"), or("*"), or("("), and("3"), or("+"), or("("), and("16"), or("-"),
                        and("9"), or(")"), or(")")
                ),
                list
        );

        assertNotEquals(
                Arrays.asList(
                        or("-"), and("2"), or("*("), and("3"), or("+("), and("16"), "-",
                        and("9"), or("))")
                ),
                list
        );
    }

    @Test
    public void extractComplexOperatorTest() {
        List<Pair<String, String>> list = d.input("5*sin(2)");
        assertNotEquals(Arrays.asList("5", "*sin(", "2", ")"), list);
        assertEquals(Arrays.asList(and("5"), or("*"), or("sin("), and("2"), or(")")), list);
    }

    private static Pair<String, String> or(String s) {
        return new Pair<>(IDemo.TYPE_OR, s);
    }

    private static Pair<String, String> and(String s) {
        return new Pair<>(IDemo.TYPE_AND, s);
    }

}