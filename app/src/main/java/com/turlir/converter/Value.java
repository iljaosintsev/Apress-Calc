package com.turlir.converter;

class Value implements Member {

    private final double mValue;

    Value(double value) {
        mValue = value;
    }

    @Override
    public boolean operand() {
        return true;
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
