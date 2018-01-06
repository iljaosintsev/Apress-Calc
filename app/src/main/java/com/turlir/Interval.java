package com.turlir;

import android.support.annotation.NonNull;

class Interval {

    @NonNull
    final String value;
    private final boolean type;

    Interval(boolean operand, @NonNull String value) {
        this.value = value;
        this.type = operand;
    }

    boolean operand() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interval interval = (Interval) o;

        if (type != interval.type) return false;
        return value.equals(interval.value);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 139 * result + (type ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Interval{" +
               "value='" + value + '\'' +
               ", type=" + type +
               '}';
    }
}
