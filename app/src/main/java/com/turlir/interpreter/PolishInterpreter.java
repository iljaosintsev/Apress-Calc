package com.turlir.interpreter;


import java.util.ArrayDeque;
import java.util.Deque;

public class PolishInterpreter implements NotationInterpreter {

    private final Deque<Double> mQueue;

    public PolishInterpreter() {
        mQueue = new ArrayDeque<>();
    }

    @Override
    public double poolDigit() {
        return mQueue.pop();
    }

    @Override
    public void pushDigit(double value) {
        mQueue.push(value);
    }

}
