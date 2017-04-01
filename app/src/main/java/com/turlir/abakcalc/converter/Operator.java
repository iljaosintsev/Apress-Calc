package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.util.HashMap;
import java.util.Map;

enum Operator implements Item {

    MULTIPLY("*") {
        @Override
        public int priority() {
            return 3;
        }

        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(one * two);
        }
    },

    DIVIDE("/") {
        @Override
        public int priority() {
            return 3;
        }

        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(two / one);
        }
    },

    ADD("+") {
        @Override
        public int priority() {
            return 2;
        }

        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(one + two);
        }
    },

    REMOVE("-") {
        @Override
        public int priority() {
            return 2;
        }

        @Override
        public boolean associate() {
            return true;
        }

        @Override
        public void operate(NotationInterpreter visitor) {
            Double one = visitor.poolDigit();
            Double two = visitor.poolDigit();
            visitor.pushDigit(two - one);
        }
    },

    OS("(") {
        @Override
        public int priority() {
            return 1;
        }

        @Override
        public void operate(NotationInterpreter visitor) {
            //
        }
    },

    CS(")") {
        @Override
        public int priority() {
            return 1;
        }

        @Override
        public void operate(NotationInterpreter visitor) {
            //
        }
    };

    private final String mOperator;

    private static final Map<String, Operator> map;
    static {
        map = new HashMap<>();
        for (Operator sign : Operator.values()) {
            map.put(sign.mOperator, sign);
        }
    }

    public static Operator find(String sign) {
        return map.get(sign);
    }


    Operator(String op) {
        mOperator = op;
    }

    public abstract int priority();

    public boolean associate() {
        return false;
    }

}
