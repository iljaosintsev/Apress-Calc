package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.util.ArrayDeque;
import java.util.Deque;

public class PolishInterpreter implements NotationInterpreter {

    private final Deque<Double> mQueue;

    public PolishInterpreter() {
        mQueue = new ArrayDeque<>();
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
