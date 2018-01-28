package com.turlir.converter;

import com.turlir.interpreter.NotationInterpreter;

import java.text.DecimalFormat;

public class Value implements Member {

    private static final DecimalFormat DF = new DecimalFormat("#.###"); // формат результата

    private final double mValue;

    public Value(double value) {
        mValue = value;
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
    public void print(StringBuilder chain) {
        chain.append(DF.format(mValue));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value = (Value) o;

        return Double.compare(value.mValue, mValue) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(mValue);
        return (int) (temp ^ (temp >>> 32));
    }
}
