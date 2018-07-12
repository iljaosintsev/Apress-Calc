package com.turlir.converter;

import java.math.BigDecimal;

public interface Printer {

    void append(String part);

    void append(BigDecimal part);

    int length();

    void reset();
}
