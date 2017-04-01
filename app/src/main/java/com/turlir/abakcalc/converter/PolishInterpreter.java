package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.util.Stack;

public class PolishInterpreter implements NotationInterpreter {

    private final Stack<Double> mQueue;

    public PolishInterpreter() {
        mQueue = new Stack<>();
    }

    @Override
    public Double poolDigit() {
        return mQueue.pop();
    }

    @Override
    public void pushDigit(Double value) {
        mQueue.push(value);
    }

}
