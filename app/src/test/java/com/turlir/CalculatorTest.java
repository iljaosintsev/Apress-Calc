package com.turlir;

public class CalculatorTest {

    /*@Test
    public void calc() throws Exception {
        PolishConverter converter = mock(PolishConverter.class);

        Queue<Item> queue = new LinkedList<>();
        queue.add(new Operand(2.0));
        queue.add(new Operand(2.0));
        queue.add(Operators.PLUS);

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

    @Test
    public void unaryMinusTest() {
        PolishConverter converter = mock(PolishConverter.class);

        Queue<Item> queue = new LinkedList<>();
        queue.add(new Operand(4.0));
        queue.add(new Operand(-3.0));
        queue.add(Operators.MULTIPLY);

        when(converter.convert("4 * -3")).thenReturn(queue);
        //
        NotationInterpreter interpreter = mock(NotationInterpreter.class);
        when(interpreter.poolDigit()).thenReturn(4.0);
        when(interpreter.poolDigit()).thenReturn(-3.0);
        when(interpreter.poolDigit()).thenReturn(-12.0);
        //
        Calculator calc = new Calculator(converter, interpreter);
        Double actual = calc.calc("4 * -3");
        assertEquals(-12.0, actual, 0.1);
    }

    @Test
    public void plusMinusTest() {
        PolishConverter cov = new PolishConverter();
        NotationInterpreter inter = new PolishInterpreter();
        Calculator calc = new Calculator(cov, inter);
        Double res = calc.calc("8 - 2 + 1");
        assertEquals(7, res, 0.1);
    }

    @Test
    public void multiplyDivideTest() {
        PolishConverter cov = new PolishConverter();
        NotationInterpreter inter = new PolishInterpreter();
        Calculator calc = new Calculator(cov, inter);
        Double res = calc.calc("4 * 4 / 8");
        assertEquals(2, res, 0.1);
    }

    @Test
    public void priorityTest() {
        PolishConverter cov = new PolishConverter();
        NotationInterpreter inter = new PolishInterpreter();
        Calculator calc = new Calculator(cov, inter);
        Double res = calc.calc("2 + 2 * 2");
        assertEquals(6, res, 0.1);
    }

    @Test
    public void complexTest() {
        PolishConverter cov = new PolishConverter();
        NotationInterpreter inter = new PolishInterpreter();
        Calculator calc = new Calculator(cov, inter);
        Double res = calc.calc("1 + 25 - 5 / 22 + ( 45 + 34 ) * -3");
        assertEquals(-211.23, res, 0.1);
    }*/

}