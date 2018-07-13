package com.turlir.converter;

import android.widget.EditText;

import com.turlir.interpreter.NotationInterpreter;

import java.math.BigDecimal;
import java.math.MathContext;

public class Value implements Member {

    private final BigDecimal mValue;
    private final boolean isFloat;

    public Value(double value) {
        mValue = new BigDecimal(value, MathContext.UNLIMITED);
        isFloat = false;
    }

    Value(String value, boolean contains) {
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
        return new Visual() {
            @Override
            public void print(Printer chain) {
                chain.append(mValue);
                if (isFloat) chain.appendSeparator();
            }

            @Override
            public boolean selectionConstraint(int selStart, int selEnd, int length) {
                return false;
            }

            @Override
            public void interceptSelection(EditText editor, int a, int b) {
                //
            }

            @Override
            public int constraintStart() {
                return 0;
            }

            @Override
            public int constraintEnd() {
                return 0;
            }

            @Override
            public String toString() {
                return mValue.toString();
            }
        };
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
