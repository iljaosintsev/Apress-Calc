package com.turlir.calculator.translator;

import com.turlir.calculator.converter.Member;
import com.turlir.calculator.member.Operator;
import com.turlir.calculator.member.Operators;
import com.turlir.calculator.member.Value;
import com.turlir.calculator.converter.Visual;
import com.turlir.calculator.interpreter.NotationInterpreter;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PolishTranslatorTest {

    private static final Member OS = Operators.OS;

    private static final Operator CS = Operators.CS;

    private static final Member PLUS = new StubOperator("+", 2);

    private static final Member MINUS = new StubOperator("-", 2);

    private static final Member MULTI = new StubOperator("*", 3);

    private static final Member DIV = new StubOperator("/", 3);

    private static final Member UNARY = new StubOperator("-", 4);

    private NotationTranslator sor;

    @Before
    public void setup() {
        sor = new PolishTranslator();
    }

    @Test
    public void plusTest() throws Exception {
        Iterator<Member> sequence = seq(new Value(2), PLUS, new Value(3));
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(2), new Value(3), PLUS);
    }

    @Test
    public void priorityTest() throws Exception  {
        Iterator<Member> sequence = seq(new Value(2), PLUS, new Value(3), MULTI, new Value(7), MINUS, new Value(4), DIV, new Value(2));
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(2), new Value(3), new Value(7), MULTI, PLUS, new Value(4), new Value(2), DIV, MINUS);
    }

    @Test
    public void bracketTest() throws Exception  {
        Iterator<Member> sequence = seq(new Value(2), PLUS, OS, new Value(6), MINUS, new Value(5), CS);
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(2), new Value(6),  new Value(5), MINUS, PLUS);

        sequence = seq(new Value(2), PLUS, OS, new Value(6), MINUS, new Value(4), CS, MULTI, new Value(3));
        lst = sor.translate(sequence);
        check(lst, new Value(2), new Value(6),  new Value(4), MINUS, new Value(3), MULTI, PLUS);
    }

    @Test
    public void unaryMinus() throws Exception  {
        Iterator<Member> sequence = seq(UNARY, new Value(4), PLUS, new Value(2));
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(4), UNARY, new Value(2), PLUS);
    }

    @Test
    public void unaryMinusInsideExp() throws Exception  {
        Iterator<Member> sequence = seq(new Value(4), PLUS, new Value(2), MULTI, UNARY, new Value(1));
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(4), new Value(2), MULTI, PLUS, new Value(1), UNARY);
    }

    private static void check(Queue<Member> actual, Member... exp) {
        assertNotNull(actual);
        assertEquals(exp.length, actual.size());
        assertThat(actual, CoreMatchers.hasItems(exp));
    }

    private static Iterator<Member> seq(Member... member) {
        return Arrays.asList(member).iterator();
    }

    private static class StubOperator extends Operator {

        private StubOperator(String token, int priority) {
            super(token, priority);
        }

        @Override
        public void process(NotationInterpreter interpreter) {
            throw new IllegalStateException();
        }

        @Override
        public Visual view() {
            throw new IllegalStateException();
        }
    }

}