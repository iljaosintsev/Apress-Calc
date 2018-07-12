package com.turlir.extractors;

import java.util.Iterator;

public interface IntervalExtractor {

    Iterator<Interval> iterator(String value);

}
