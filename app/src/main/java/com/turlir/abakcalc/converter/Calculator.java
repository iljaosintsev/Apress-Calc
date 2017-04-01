package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.util.Queue;

/**
 * Простой класс для организации взаимодействия между {@link PolishConverter} и {@link NotationInterpreter}
 */
public class Calculator {

    private final PolishConverter mConverter;
    private final NotationInterpreter mInter;

    public Calculator(PolishConverter converter, NotationInterpreter inter) {
        mConverter = converter;
        mInter = inter;
    }

    public Double calc(String exp) {
        Queue<Item> queue = mConverter.convert(exp);

        for (Item current : queue) {
            current.operate(mInter);
        }

        return mInter.poolDigit();
    }

}
