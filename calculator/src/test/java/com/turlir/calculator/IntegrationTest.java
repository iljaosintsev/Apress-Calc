package com.turlir.calculator;

import com.turlir.calculator.converter.ExpressionExtractor;
import com.turlir.calculator.converter.MemberConverter;
import com.turlir.calculator.extractors.ExpressionPartExtractor;
import com.turlir.calculator.extractors.MultiOperatorExtractor;
import com.turlir.calculator.interpreter.NotationInterpreter;
import com.turlir.calculator.interpreter.PolishInterpreter;
import com.turlir.calculator.translator.NotationTranslator;
import com.turlir.calculator.translator.PolishTranslator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private Calculator calc;
    private Analyzer mAnalyzer;

    @Before
    public void setup() {
        ExpressionExtractor conv = new MemberConverter(
                new MultiOperatorExtractor(
                        new ExpressionPartExtractor()
                )
        );
        NotationTranslator trans = new PolishTranslator();
        NotationInterpreter inter = new PolishInterpreter();
        calc = new Calculator(trans, inter);
        mAnalyzer = new Analyzer(conv);
    }

    @Test
    public void plusMinusTest() throws Exception {
        Double res = calc.calc(mAnalyzer.analyze("8 - 2 + 1")).doubleValue();
        assertEquals(7, res, 0.1);
    }


    @Test
    public void multiplyDivideTest() throws Exception {
        Double res = calc.calc(mAnalyzer.analyze("4 * 4 / 8")).doubleValue();
        assertEquals(2, res, 0.1);
    }

    @Test
    public void priorityTest() throws Exception {
        Double res = calc.calc(mAnalyzer.analyze("2 + 2 * 2")).doubleValue();
        assertEquals(6, res, 0.1);
    }

    @Test
    public void floatingNumberTest() throws Exception {
        Double res = calc.calc(mAnalyzer.analyze("2,2 + 3")).doubleValue();
        assertEquals(5.2, res, 0.1);
    }

    @Test
    public void complexTest() throws Exception {
        Double res = calc.calc(mAnalyzer.analyze("1 + 25 - 5 / 22")).doubleValue();
        assertEquals(25.8, res, 0.1);

        res = calc.calc(mAnalyzer.analyze("1 + 25 - 5 / 22 + ( 45 + 34 )")).doubleValue();
        assertEquals(104.8, res, 0.1);

        res = calc.calc(mAnalyzer.analyze("104.8 * -3")).doubleValue();
        assertEquals(-314.4, res, 0.1);

        res = calc.calc(mAnalyzer.analyze("( 45 + 34 ) * -3")).doubleValue();
        assertEquals(-237, res, 0.1);

        res = calc.calc(mAnalyzer.analyze("1 + 25 - 5 / 22 + ( 45 + 34 ) * -3")).doubleValue();
        assertEquals(-211.23, res, 0.1);
    }

}