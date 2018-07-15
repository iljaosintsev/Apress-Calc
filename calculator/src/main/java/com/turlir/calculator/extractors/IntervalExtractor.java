package com.turlir.calculator.extractors;

import java.util.Iterator;

public interface IntervalExtractor {

    Iterator<Interval> iterator(String value);

}
