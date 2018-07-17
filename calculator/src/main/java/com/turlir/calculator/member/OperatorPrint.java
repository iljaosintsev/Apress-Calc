package com.turlir.calculator.member;


import com.turlir.calculator.converter.Printer;
import com.turlir.calculator.converter.Visual;

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
