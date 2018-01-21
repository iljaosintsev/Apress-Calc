package com.turlir;

import com.turlir.interpreter.NotationInterpreter;
import com.turlir.interpreter.PolishInterpreter;
import com.turlir.translator.NotationTranslator;
import com.turlir.translator.PolishTranslator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private Calculator calc;

    @Before
    public void setup() {
        NotationTranslator cov = new PolishTranslator();
        NotationInterpreter inter = new PolishInterpreter();
        calc = new Calculator(cov, inter);
    }

    @Test
    public void plusMinusTest() {
        Double res = calc.calc("8 - 2 + 1");
        assertEquals(7, res, 0.1);
    }

    @Test
    public void multiplyDivideTest() {
        Double res = calc.calc("4 * 4 / 8");
        assertEquals(2, res, 0.1);
    }

    @Test
    public void priorityTest() {
        Double res = calc.calc("2 + 2 * 2");
        assertEquals(6, res, 0.1);
    }

    @Test
    public void complexTest() {
        Double res = calc.calc("1 + 25 - 5 / 22");
        assertEquals(25.8, res, 0.1);

        res = calc.calc("1 + 25 - 5 / 22 + ( 45 + 34 )");
        assertEquals(104.8, res, 0.1);

        res = calc.calc("104.8 * -3");
        assertEquals(-314.4, res, 0.1);

        res = calc.calc("( 45 + 34 ) * -3");
        assertEquals(-237, res, 0.1);

        res = calc.calc("1 + 25 - 5 / 22 + ( 45 + 34 ) * -3");
        assertEquals(-211.23, res, 0.1);
    }

}