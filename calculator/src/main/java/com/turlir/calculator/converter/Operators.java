package com.turlir.calculator.converter;

import com.turlir.calculator.interpreter.NotationInterpreter;

import java.math.BigDecimal;
import java.math.MathContext;

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
            BigDecimal a = interpreter.poolDigit();
            BigDecimal b = interpreter.poolDigit();
            interpreter.pushDigit(a.add(b));
        }
    };

    static final Member MINUS = new Operator(" - ", 2) {

        @Override
        public void process(NotationInterpreter interpreter) {
            BigDecimal a = interpreter.poolDigit();
            BigDecimal b = interpreter.poolDigit();
            interpreter.pushDigit(b.subtract(a));
        }
    };

    static final Member MULTI = new Operator(" * ", 3) {

        @Override
        public void process(NotationInterpreter interpreter) {
            BigDecimal a = interpreter.poolDigit();
            BigDecimal b = interpreter.poolDigit();
            interpreter.pushDigit(a.multiply(b));
        }
    };

    static final Member DIV = new Operator(" / ", 3) {

        @Override
        public void process(NotationInterpreter interpreter) {
            BigDecimal a = interpreter.poolDigit();
            BigDecimal b = interpreter.poolDigit();
            interpreter.pushDigit(b.divide(a, MathContext.DECIMAL64));
        }
    };

    static final Member UNARY_MINUS = new Operator("-", 4) {

        private final BigDecimal MULTIPLICAND = new BigDecimal(-1);

        @Override
        public void process(NotationInterpreter interpreter) {
            BigDecimal a = interpreter.poolDigit();
            interpreter.pushDigit(a.multiply(MULTIPLICAND));
        }
    };

    public static Member find(String token) {
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
