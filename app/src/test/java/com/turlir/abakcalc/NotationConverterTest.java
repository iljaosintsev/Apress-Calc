package com.turlir.abakcalc;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

public class NotationConverterTest {

    private NotationConverter nc;

    @Before
    public void setUp() {
        nc = new NotationConverter();
    }

    private static final Item one = new Operand(1.0);
    private static final Item two = new Operand(2.0);
    private static final Item three = new Operand(3.0);
    private static final Item four = new Operand(4.0);

    private static final Item add = Operator.ADD;
    private static final Item remove = Operator.REMOVE;
    private static final Item multi = Operator.MULTIPLY;
    private static final Item div = Operator.DIVIDE;

    private void check(Queue<Item> actual, Item... expected ) {
        Queue<Item> items = new LinkedList<>(Arrays.asList(expected));
        assertEquals(items, actual);
    }

    @Test
    public void simpleTest() {
        Queue<Item> calc = nc.convert("2 + 3");
        check(calc, two, three, add);
    }

    @Test
    public void simpleBracketTest() {
        Queue<Item>  calc = nc.convert("( 2 + 3 )");
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
    public void bracketFailTest() {
        nc.convert("( 1 + 2 ");
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

}