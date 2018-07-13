package com.turlir.abakcalc;

import com.turlir.converter.Printer;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DirectPrinter implements Printer {

    private final StringBuilder mBuilder;
    private final DecimalFormat mFormat;

    public DirectPrinter(DecimalFormat format) {
        mFormat = format;
        mBuilder = new StringBuilder();
    }

    @Override
    public void append(String part) {
        mBuilder.append(part);
    }

    @Override
    public void append(BigDecimal part) {
        String str = mFormat.format(part);
        append(str);
    }

    @Override
    public void appendSeparator() {
        append(String.valueOf(mFormat.getDecimalFormatSymbols().getDecimalSeparator()));
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
