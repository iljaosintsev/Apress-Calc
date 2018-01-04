package com.turlir.abakcalc.converter.abs;


import android.support.annotation.NonNull;

public abstract class Operator implements Item, Comparable<Operator> {

    private final String mToken;
    private final int mPriority;

    protected Operator(String token, int priority) {
        mToken = token;
        mPriority = priority;
    }

    @Override
    public int compareTo(@NonNull Operator o) {
        return mPriority - o.mPriority;
    }

    @Override
    public String toString() {
        return mToken;
    }
}
