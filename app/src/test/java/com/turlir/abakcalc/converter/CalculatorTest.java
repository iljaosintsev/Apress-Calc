package com.turlir.abakcalc.converter;

import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorTest {

    @Test
    public void calc() throws Exception {
        PolishConverter converter = mock(PolishConverter.class);

        Queue<Item> queue = new LinkedList<>();
        queue.add(new Operand(2.0));
        queue.add(new Operand(2.0));
        queue.add(Operator.ADD);

        when(converter.convert("2 + 2")).thenReturn(queue);
        //
        NotationInterpreter interpreter = mock(NotationInterpreter.class);
        when(interpreter.poolDigit()).thenReturn(2.0);
        when(interpreter.poolDigit()).thenReturn(2.0);
        when(interpreter.poolDigit()).thenReturn(4.0);
        //
        Calculator calc = new Calculator(converter, interpreter);
        Double actual = calc.calc("2 + 2");
        assertEquals(4.0, actual, 0.1);
    }

}