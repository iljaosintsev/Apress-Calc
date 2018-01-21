package com.turlir.translator;

import com.turlir.converter.Member;
import com.turlir.converter.Part;
import com.turlir.converter.Parts;
import com.turlir.converter.Value;
import com.turlir.interpreter.NotationInterpreter;

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

    private static final Member OS = Parts.OS;

    private static final Part CS = Parts.CS;

    private static final Member PLUS = new Part("+", 2) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a + b);
        }
    };

    private static final Member MINUS = new Part("-", 2) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a - b);
        }
    };

    private static final Member MULTI = new Part("*", 3) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a * b);
        }
    };

    private static final Member DIV = new Part("/", 3) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a / b);
        }
    };

    private NotationTranslator sor;

    @Before
    public void setup() {
        sor = new PolishTranslator();
    }

    @Test
    public void plusTest() {
        Iterator<Member> sequence = seq(new Value(2), PLUS, new Value(3));
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(2), new Value(3), PLUS);
    }

    @Test
    public void priorityTest() {
        Iterator<Member> sequence = seq(new Value(2), PLUS, new Value(3), MULTI, new Value(7), MINUS, new Value(4), DIV, new Value(2));
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(2), new Value(3), new Value(7), MULTI, PLUS, new Value(4), new Value(2), DIV, MINUS);
    }

    @Test
    public void bracketTest() {
        Iterator<Member> sequence = seq(new Value(2), PLUS, OS, new Value(6), MINUS, new Value(5), CS);
        Queue<Member> lst = sor.translate(sequence);
        check(lst, new Value(2), new Value(6),  new Value(5), MINUS, PLUS);
    }

    private static void check(Queue<Member> actual, Member... exp) {
        assertNotNull(actual);
        assertEquals(exp.length, actual.size());
        assertThat(actual, CoreMatchers.hasItems(exp));
    }

    private static Iterator<Member> seq(Member... member) {
        return Arrays.asList(member).iterator();
    }

}