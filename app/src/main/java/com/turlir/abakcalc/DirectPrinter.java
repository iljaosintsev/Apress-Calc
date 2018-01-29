package com.turlir.abakcalc;

import com.turlir.converter.Printer;

public class DirectPrinter implements Printer {

    private final StringBuilder mBuilder;

    DirectPrinter() {
        mBuilder = new StringBuilder();
    }

    @Override
    public void append(String part) {
        mBuilder.append(part);
    }

    @Override
    public int length() {
        return mBuilder.length();
    }

    @Override
    public void reset() {
        mBuilder.delete(0, length());
    }

    @Override
    public String toString() {
        return mBuilder.toString();
    }
}
