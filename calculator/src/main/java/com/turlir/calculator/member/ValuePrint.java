package com.turlir.calculator.member;

import com.turlir.calculator.converter.Printer;
import com.turlir.calculator.converter.Visual;

import java.math.BigDecimal;

class ValuePrint implements Visual {

    private final BigDecimal mValue;
    private final boolean isFloat;

    ValuePrint(BigDecimal value, boolean shouldFloat) {
        mValue = value;
        isFloat = shouldFloat;
    }

    @Override
    public void print(Printer chain) {
        chain.append(mValue);
        if (isFloat) chain.appendSeparator();
    }

    @Override
    public String toString() {
        return mValue.toString();
    }
}
