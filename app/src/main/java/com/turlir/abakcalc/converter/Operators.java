package com.turlir.abakcalc.converter;

import com.turlir.abakcalc.converter.abs.NotationInterpreter;
import com.turlir.abakcalc.converter.abs.Operator;

abstract class Operators {

    static Operator find(String sign) {
        switch (sign) {
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "(":
                return OS;
            case ")":
                return CS;
            default:
                throw new IllegalArgumentException("Операция не доступна");
        }
    }

    static final Operator MULTIPLY = new Operator("*", 3) {
        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(one * two);
        }
    };

    static final Operator DIVIDE = new Operator("/", 3) {
        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(two / one);
        }
    };

    static final Operator PLUS = new Operator("+", 2) {
        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(one + two);
        }
    };

    static final Operator MINUS = new Operator("-", 2) {
        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(two - one);
        }
    };

    static final Operator OS = new Operator("(", 1) {
        @Override
        public void operate(NotationInterpreter visitor) throws RuntimeException {
            //
        }
    };

    static final Operator CS = new Operator(")", 1) {
        @Override
        public void operate(NotationInterpreter visitor) throws RuntimeException {
            //
        }
    };
}
