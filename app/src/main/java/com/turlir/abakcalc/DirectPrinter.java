package com.turlir.abakcalc;

import com.turlir.converter.Printer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DirectPrinter implements Printer {

    private static final DecimalFormat DF = new DecimalFormat();
    private static final DecimalFormatSymbols FORMAT = new DecimalFormatSymbols(Locale.getDefault());

    static {
        DF.setDecimalFormatSymbols(FORMAT);
        DF.setMaximumFractionDigits(340); // see doc DecimalFormat
        DF.setMinimumIntegerDigits(1);
    }

    private final StringBuilder mBuilder;

    public DirectPrinter() {
        mBuilder = new StringBuilder();
    }

    @Override
    public void append(String part) {
        mBuilder.append(part);
    }

    @Override
    public void append(BigDecimal part) {
        String str = DF.format(part);
        append(str);
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
