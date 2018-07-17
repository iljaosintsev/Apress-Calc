package com.turlir.calculator.member;

import com.turlir.calculator.converter.Member;
import com.turlir.calculator.converter.Visual;
import com.turlir.calculator.interpreter.NotationInterpreter;

import java.math.BigDecimal;
import java.math.MathContext;

public class Value implements Member {

    private final BigDecimal mValue;
    private final boolean isFloat;

    public Value(double value) {
        mValue = new BigDecimal(value, MathContext.UNLIMITED);
        isFloat = false;
    }

    public Value(String value, boolean contains) {
        mValue = new BigDecimal(value, MathContext.UNLIMITED);
        isFloat = contains;
    }

    @Override
    public boolean operand() {
        return true;
    }

    @Override
    public void process(NotationInterpreter interpreter) {
        interpreter.pushDigit(mValue);
    }

    @Override
    public Visual view() {
        return new ValuePrint(mValue, isFloat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value = (Value) o;

        return value.mValue.compareTo(mValue) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(mValue.doubleValue());
        return (int) (temp ^ (temp >>> 32));
    }

}
