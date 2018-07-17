package com.turlir.calculator.converter;


class OperatorPrint implements Visual {

    private final String mToken;

    OperatorPrint(String token) {
        mToken = token;
    }

    @Override
    public void print(Printer chain) {
        chain.append(mToken);
    }

    @Override
    public String toString() {
        return mToken;
    }
}
