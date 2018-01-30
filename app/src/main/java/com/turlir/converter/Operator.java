package com.turlir.converter;

import android.support.annotation.NonNull;
import android.util.Log;

public abstract class Operator implements Member, Comparable<Operator> {

    private final int mPriority;
    private final Visual mVisual;

    protected Operator(String token, int priority) {
        mPriority = priority;
        Log.d("Operator", "create operator and visual");
        mVisual = new OperatorVisual(token);
    }

    @Override
    public int compareTo(@NonNull Operator o) {
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
