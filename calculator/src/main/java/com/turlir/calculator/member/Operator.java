package com.turlir.calculator.member;

import com.turlir.calculator.converter.Member;
import com.turlir.calculator.converter.Visual;

public abstract class Operator implements Member, Comparable<Operator> {

    private final int mPriority;
    private final Visual mVisual;

    protected Operator(String token, int priority) {
        mPriority = priority;
        mVisual = new OperatorPrint(token);
    }

    @Override
    public int compareTo(Operator o) {
        return mPriority - o.mPriority;
    }

    @Override
    public boolean operand() {
        return false;
    }

    @Override
    public Visual view() {
        return mVisual;
    }
}
