package com.turlir.abakcalc;


class Operand implements Item {

    private final Double mValue;

    Operand(Double value) {
        mValue = value;
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

    @Override
    public int hashCode() {
        return mValue != null ? mValue.hashCode() : 0;
    }

}
