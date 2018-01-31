package com.turlir.converter;

import android.widget.EditText;

import com.turlir.interpreter.NotationInterpreter;

import java.text.DecimalFormat;

public class Value implements Member {

    private static final DecimalFormat DF = new DecimalFormat("###,###,###.###");
    private static final String SEPARATOR = String.valueOf(DF.getDecimalFormatSymbols().getDecimalSeparator());

    private final double mValue;
    private final boolean isFloat;

    public Value(double value) {
        mValue = value;
        isFloat = false;
    }

    Value(double value, boolean contains) {
        mValue = value;
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
                if (isFloat) {
                    String tmp = DF.format(mValue);
                    if (!tmp.contains(SEPARATOR)) {
                        tmp += SEPARATOR;
                    }
                    chain.append(tmp);
                } else {
                    chain.append(DF.format(mValue));
                }
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
        };
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
