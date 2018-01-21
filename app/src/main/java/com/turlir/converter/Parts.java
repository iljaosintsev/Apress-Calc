package com.turlir.converter;

import com.turlir.interpreter.NotationInterpreter;

public abstract class Parts {

    public static final Member OS = new Part("(", 1) {
        @Override
        public void process(NotationInterpreter interpreter) {
            //
        }
    };

    public static final Part CS = new Part(")", 1) {
        @Override
        public void process(NotationInterpreter interpreter) {
            //
        }
    };

    static final Member PLUS = new Part("+", 2) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a + b);
        }
    };

    static final Member MINUS = new Part("-", 2) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a - b);
        }
    };

    static final Member MULTI = new Part("*", 3) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a * b);
        }
    };

    static final Member DIV = new Part("/", 3) {
        @Override
        public void process(NotationInterpreter interpreter) {
            Double a = interpreter.poolDigit();
            Double b = interpreter.poolDigit();
            interpreter.pushDigit(a / b);
        }
    };

    static Member find(String token) {
        switch (token) {
            case "*":
                return MULTI;
            case "/":
                return DIV;
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
}
