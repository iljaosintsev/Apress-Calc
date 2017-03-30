package com.turlir.abakcalc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CalcTest {

    private Calc c;

    @Before
    public void setUp() {
        c = new Calc();
    }

    @Test
    public void simpleTest() {
        double calc = c.calc("2 + 3");
        assertEquals(5, calc, 0.1);
    }

    @Test
    public void simpleBracketTest() {
        double calc = c.calc("( 2 + 3 )");
        assertEquals(5, calc, 0.1);
    }

    @Test
    public void sideEffectAfterBracketTest() {
        double calc = c.calc("( 2 + 3 ) - 3");
        assertEquals(2, calc, 0.1);

        calc = c.calc("3 * ( 2 + 3 )");
        assertEquals(15, calc, 0.1);
    }

    @Test
    public void simpleQueueTest() {
        double calc = c.calc("3 + 4 * 2");
        assertEquals(11, calc, 0.1);

        calc = c.calc("2 + 3 * 4");
        assertEquals(14, calc, 0.1);
    }

    @Test
    public void associativeQueueTest() {
        double calc;

        calc = c.calc("1 + 4 * 2 + 3");
        assertEquals(12, calc, 0.1);

        calc = c.calc("1 * 4 + 2 + 3");
        assertEquals(9, calc, 0.1);

        calc = c.calc("5 - 1 * 2 + 3");
        assertEquals(6, calc, 0.1);

        calc = c.calc("1 + 4 - 2 + 3");
        assertEquals(6, calc, 0.1);

        calc = c.calc("5 - 3 - 1 - 2");
        assertEquals(-1, calc, 0.1);
    }

    @Test
    public void queueAndBracketTest() {
        double calc = c.calc("( 1 + 2 ) * 4 + 3");
        assertEquals("first var", 15, calc, 0.1);

        calc = c.calc("4 * ( 1 + 2 ) + 3");
        assertEquals("second var", 15, calc, 0.1);

        calc = c.calc("3 + ( 1 + 2 ) * 4");
        assertEquals("three var", 15, calc, 0.1);
    }

    @Test
    public void associativeQueueBracketTest() {
        double calc = c.calc("( 1 + 4 ) - 2 + 3");
        assertEquals(6, calc, 0.1);
    }

    @Test(expected = RuntimeException.class)
    public void bracketFailTest() {
        c.calc("( 1 + 2 ");
        // assertEquals(3, calc, 0.1);
    }

    @Test(expected = ArithmeticException.class)
    public void divisionByZeroTest() {
        c.calc("3 / ( 4 - 4 )");
    }

    @Test(expected = ArithmeticException.class)
    public void twoDivisionByZeroTest() {
        c.calc("( 5 - 4 ) / 0");
    }

    @Test(expected = ArithmeticException.class)
    public void threeDivisionByZeroTest() {
        c.calc("( 5 - 4 ) / ( 3 - 3 )");
    }

    @Test
    public void operandExpectedCorrectionTest() {
        double actual = c.calc("2 + 3 *");
        assertEquals(5.0, actual, 0.1);

        actual = c.calc("2 * 3 +");
        assertEquals(6.0, actual, 0.1);
    }

    @Test(expected = ArithmeticException.class)
    public void invalidExpressionTest() {
        c.calc("2 + ");
    }

    @Test(expected = ArithmeticException.class)
    public void invalidPriorityExpressionTest() {
        c.calc("2 * ");
    }

    @Test(expected = ArithmeticException.class)
    public void invalidAssociativeExpressionTest() {
        c.calc("2 - ");
    }

    @Test
    public void unarMinusTest() {
        Double actual;

        actual = c.calc("5 * - 3");
        assertEquals(-15.0, actual, 0.1);

        actual = c.calc("- 3 * 5");
        assertEquals(-15.0, actual, 0.1);

        actual = c.calc("- 3 * ( 5 + 5 )");
        assertEquals(-30.0, actual, 0.1);

        actual = c.calc("( 5 + 5 ) * - 3");
        assertEquals(-30.0, actual, 0.1);
    }

    @Test
    public void hardTest() {
        Double actual;

        actual = c.calc("1 + 2 * 5 - 5 / 22");
        assertEquals(10.77, actual, 0.01);

        actual = c.calc("1 + 2 * 5 - 5 / 22 + ( 45 + 34 ) * 3");
        assertEquals(247.77, actual, 0.01);

        actual = c.calc("1 + 2 * 5 - 5 / 22 + ( 45 + 34 ) * - 3");
        assertEquals(-226.23, actual, 0.01);
    }

    @Test
    public void doubleTest() {
        Double calc = c.calc("5.2 + 0.2");
        assertEquals(5.4, calc, 0.1);
    }

}