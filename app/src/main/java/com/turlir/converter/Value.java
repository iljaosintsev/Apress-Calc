package com.turlir.converter;

import android.widget.EditText;

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
    public Visual view() {
        return new Visual() {
            @Override
            public void print(Printer chain) {
                chain.append(DF.format(mValue));
            }

            @Override
            public boolean selectionConstraint(int selEnd) {
                return false;
            }

            @Override
            public void interceptSelection(EditText editor) {
                //
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
