package com.turlir.converter;

abstract class Parts {

    static final Member OS = new Part("(", 1);

    static final Part CS = new Part(")", 1);

    static final Member PLUS = new Part("+", 2);

    private static final Member MINUS = new Part("-", 2);

    private static final Member MULTI = new Part("*", 3);

    private static final Member DIV = new Part("/", 3);

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
