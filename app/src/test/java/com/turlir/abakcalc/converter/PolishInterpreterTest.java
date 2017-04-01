package com.turlir.abakcalc.converter;

import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolishInterpreterTest {

    private NotationInterpreter mInter;

    @Before
    public void setUp() {
        mInter = new PolishInterpreter();
    }

    @Test
    public void singlePoolTest() {
        mInter = new PolishInterpreter();
        mInter.pushDigit(2.0);
        Double actual = mInter.poolDigit();
        assertEquals(2.0, actual, 0.1);
    }

    @Test
    public void multiPollTest() {
        double[] array = new double[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        mInter = new PolishInterpreter();
        for (double digit : array) {
            mInter.pushDigit(digit);
        }

        for (double digit : array) {
            Double actual = mInter.poolDigit();
            Double expected = digit;
            assertEquals(expected, actual, 0.1);
        }
    }

}