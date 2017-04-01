package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.util.Queue;

public class Calculator {

    private PolishConverter mConverter;
    private final NotationInterpreter mInter;

    public Calculator(PolishConverter converter, NotationInterpreter inter) {
        mConverter = converter;
        this.mInter = inter;
    }

    Double calc(String exp) {
        Queue<Item> queue = mConverter.convert(exp);

        for (Item current : queue) {
            current.operate(mInter);
        }

        Double result = mInter.poolDigit();
        return result;
    }

}
