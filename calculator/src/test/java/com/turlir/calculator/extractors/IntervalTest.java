package com.turlir.calculator.extractors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalTest {

    @Test
    public void firstOperatorTest() {
        Interval p = new Interval("23.54+45.67", 0);
        assertEquals("23.54", p.getValue());
        assertTrue(p.operand());
    }

    @Test
    public void operandTest() {
        Interval p = new Interval("23.54+45.67", 5);
        assertEquals("+", p.getValue());
        assertFalse(p.operand());
    }

    @Test
    public void secondOperatorTest() {
        Interval p = new Interval("23.54+45.67", 6);
        assertEquals("45.67", p.getValue());
        assertTrue(p.operand());
    }

}