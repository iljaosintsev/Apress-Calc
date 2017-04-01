package com.turlir.abakcalc;


import java.util.HashMap;
import java.util.Map;

enum Operator implements Item {

    MULTIPLY("*") {
        @Override
        public int priority() {
            return 3;
        }

        @Override
        public Double apply(Double left, Double right) {
            return left * right;
        }
    },

    DIVIDE("/") {
        @Override
        public int priority() {
            return 3;
        }

        @Override
        public Double apply(Double left, Double right) {
            if (right == 0) {
                throw new ArithmeticException("Деление на ноль");
            }
            return left / right;
        }
    },

    ADD("+") {
        @Override
        public int priority() {
            return 2;
        }

        @Override
        public Double apply(Double left, Double right) {
            return left + right;
        }
    },

    REMOVE("-") {
        @Override
        public int priority() {
            return 2;
        }

        @Override
        public Double apply(Double left, Double right) {
            return left - right;
        }

        @Override
        public boolean associate() {
            return true;
        }
    },

    OS("(") {
        @Override
        public int priority() {
            return 1;
        }

        @Override
        public Double apply(Double left, Double right) {
            return null;
        }
    },

    CS(")") {
        @Override
        public int priority() {
            return 1;
        }

        @Override
        public Double apply(Double left, Double right) {
            return null;
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

    public abstract Double apply(Double left, Double right);

    public boolean associate() {
        return false;
    }

}
