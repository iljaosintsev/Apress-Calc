package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.util.LinkedList;
import java.util.Queue;

class PolishInterpreter implements NotationInterpreter {

    private final Queue<Double> mQueue;

    PolishInterpreter() {
        mQueue = new LinkedList<>();
    }

    @Override
    public Double poolDigit() {
        return mQueue.poll();
    }

    @Override
    public void pushDigit(Double value) {
        mQueue.add(value);
    }

}
