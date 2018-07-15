package com.turlir.calculator.converter;

import java.math.BigDecimal;

public interface Printer {

    void append(String part);

    void append(BigDecimal part);

    void appendSeparator();

    int length();

    void reset();
}
