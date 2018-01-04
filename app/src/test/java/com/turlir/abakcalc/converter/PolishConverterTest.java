package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationConverter;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class PolishConverterTest {

    private static final Item one = new Operand(1.0);
    private static final Item two = new Operand(2.0);
    private static final Item three = new Operand(3.0);
    private static final Item four = new Operand(4.0);

    private static final Item add = Operators.PLUS;
    private static final Item remove = Operators.MINUS;
    private static final Item multi = Operators.MULTIPLY;
    private static final Item div = Operators.DIVIDE;

    private NotationConverter nc;

    private void check(Queue<Item> actual, Item... expected) {
        Queue<Item> items = new LinkedList<>(Arrays.asList(expected));
        assertEquals(items, actual);
    }

    @Before
    public void setUp() {
        nc = new PolishConverter();
    }

    @Test
    public void simpleTest() {
        Queue<Item> calc = nc.convert("2 + 3");
        check(calc, two, three, add);
    }

    @Test
    public void simpleBracketTest() {
        Queue<Item> calc = nc.convert("( 2 + 3 )");
        check(calc, two, three, add);
    }

    @Test
    public void sideEffectAfterBracketTest() {
        Queue<Item> calc = nc.convert("( 2 + 3 ) - 3");
        check(calc, two, three, add, three, remove);

        calc = nc.convert("3 * ( 2 + 3 )");
        check(calc, three, two, three, add, multi);
    }

    @Test
    public void simpleQueueTest() {
        Queue<Item> calc = nc.convert("3 + 4 * 2");
        check(calc, three, four, two, multi, add);

        calc = nc.convert("2 + 3 * 4");
        check(calc, two, three, four, multi, add);
    }

    @Test
    public void associativeQueueTest() {
        Queue<Item> calc;

        calc = nc.convert("1 + 4 * 2 + 3");
        check(calc, one, four, two, multi, add, three, add);

        calc = nc.convert("1 * 4 + 2 + 3");
        check(calc, one, four, multi, two, add, three, add);

        calc = nc.convert("4 - 1 * 2 + 3");
        check(calc, four, one, two, multi, remove, three, add);

        calc = nc.convert("1 + 4 - 2 + 3");
        check(calc, one, four, add, two, remove, three, add);

        calc = nc.convert("4 - 3 - 1 - 2");
        check(calc, four, three, remove, one, remove, two, remove);
    }

    @Test
    public void queueAndBracketTest() {
        Queue<Item> calc = nc.convert("( 1 + 2 ) * 4 + 3");
        check(calc, one, two, add, four, multi, three, add);

        calc = nc.convert("4 * ( 1 + 2 ) + 3");
        check(calc, four, one, two, add, multi, three, add);

        calc = nc.convert("3 + ( 1 + 2 ) * 4");
        check(calc, three, one, two, add, four, multi, add);
    }

    @Test
    public void associativeQueueBracketTest() {
        Queue<Item> calc = nc.convert("( 1 + 4 ) - 2 + 3");
        check(calc, one, four, add, two, remove, three, add);
    }

    @Test(expected = RuntimeException.class)
    public void openBracketFailTest() {
        nc.convert("( 1 + 2 ");
    }

    @Test(expected = RuntimeException.class)
    public void closedBracketFailTest() {
        nc.convert("1 + 2 )");
    }

    @Test(expected = ArithmeticException.class)
    public void digitBracketWithoutOperationTest() {
        nc.convert("2( ");
    }

    @Test
    public void divisionByZeroTest() {
        Queue<Item> calc = nc.convert("3 / ( 4 - 4 )");
        check(calc, three, four, four, remove, div);
    }

    @Test
    public void twoDivisionByZeroTest() {
        Queue<Item> calc = nc.convert("( 3 - 4 ) / 2");
        check(calc, three, four, remove, two, div);
    }

    @Test
    public void threeDivisionByZeroTest() {
        Queue<Item> calc = nc.convert("( 3 - 2 ) / ( 4 - 1 )");
        check(calc, three, two, remove, four, one, remove, div);
    }

    @Test
    public void operandExpectedCorrectionTest() {
        Queue<Item> actual = nc.convert("2 + 3 *");
        check(actual, two, three, multi, add);

        actual = nc.convert("2 * 3 +");
        check(actual, two, three, multi, add);
    }

    @Test
    public void halfExpressionTest() {
        Queue<Item> calc = nc.convert("2 + ");
        check(calc, two, add);
    }

    @Test
    public void unaryMinusTest() {
        Queue<Item> actual;

        Item th = new Operand(-3.0);

        actual = nc.convert("4 * -3");
        check(actual, four, th, multi);

        actual = nc.convert("-3 * 4");
        check(actual, th, four, multi);

        actual = nc.convert("-3 * ( 4 + 4 )");
        check(actual, th, four, four, add, multi);

        actual = nc.convert("( 4 + 4 ) * -3");
        check(actual, four, four, add, th, multi);
    }

    @Test
    public void hardTest() {
        Queue<Item> actual;

        actual = nc.convert("1 + 4 - 3 / 2");
        check(actual, one, four, add, three, two, div, remove);
    }

}