package com.turlir.interpreter;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.*;

public class PolishInterpreterTest {

    private NotationInterpreter mInter;

    @Before
    public void setUp() {
        mInter = new PolishInterpreter();
    }

    @Test
    public void singlePoolTest() {
        mInter = new PolishInterpreter();
        mInter.pushDigit(new BigDecimal(2.0));
        BigDecimal actual = mInter.poolDigit();
        assertTrue(new BigDecimal(2.0).compareTo(actual) == 0);
    }

    @Test
    public void multiPollTest() {
        BigDecimal[] array = new BigDecimal[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = new BigDecimal(i);
        }

        mInter = new PolishInterpreter();
        for (BigDecimal digit : array) {
            mInter.pushDigit(digit);
        }

        for (int i = 0; i < array.length; i++) {
            BigDecimal digit = array[array.length - i - 1];
            BigDecimal actual = mInter.poolDigit();
            assertTrue(digit.compareTo(actual) == 0);
        }
    }

}