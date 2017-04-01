package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationInterpreter;

class Operand implements Item {

    private final Double mValue;

    Operand(Double value) {
        mValue = value;
    }

    @Override
    public void operate(NotationInterpreter visitor) {
        visitor.pushDigit(mValue);
    }

    @Override
    public String toString() {
        return mValue.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operand operand = (Operand) o;

        return mValue != null ? mValue.equals(operand.mValue) : operand.mValue == null;

    }

}
