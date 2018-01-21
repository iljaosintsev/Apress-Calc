package com.turlir.converter;

import android.support.annotation.NonNull;

public abstract class Part implements Member, Comparable<Part> {

    private final String mToken;
    private final int mPriority;

    protected Part(String token, int priority) {
        mToken = token;
        mPriority = priority;
    }

    @Override
    public int compareTo(@NonNull Part o) {
        return mPriority - o.mPriority;
    }

    @Override
    public boolean operand() {
        return false;
    }

}
