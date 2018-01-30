package com.turlir.converter;

import com.turlir.interpreter.NotationInterpreter;

public abstract class Operators {

    public static final Member OS = new Operator("(", 1) {

        @Override
        public void process(NotationInterpreter interpreter) {
            //
        }
    };

    public static final Operator CS = new Operator(")", 1) {

        @Override
        public void process(NotationInterpreter interpreter) {
            //
        }
    };

    static final Member PLUS = new Operator(" + ", 2) {

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(a + b);
        }
    };

    static final Member MINUS = new Operator(" - ", 2) {

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(b - a);
        }
    };

    static final Member MULTI = new Operator(" * ", 3) {

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(a * b);
        }
    };

    static final Member DIV = new Operator(" / ", 3) {

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(b / a);
        }
    };

    static final Member UNARY_MINUS = new Operator("-", 4) {

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            interpreter.pushDigit(-a);
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
