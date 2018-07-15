package com.turlir.calculator.interpreter;


import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

public class PolishInterpreter implements NotationInterpreter {

    private final Deque<BigDecimal> mQueue;

    public PolishInterpreter() {
        mQueue = new ArrayDeque<>();
    }

    @Override
    public BigDecimal poolDigit() {
        return mQueue.pop();
    }

    @Override
    public void pushDigit(BigDecimal value) {
        mQueue.push(value);
    }

}
