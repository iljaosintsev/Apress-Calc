package com.turlir.abakcalc.converter;


import com.turlir.abakcalc.converter.abs.Item;
import com.turlir.abakcalc.converter.abs.NotationConverter;
import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.util.Queue;

/**
 * Простой класс для организации взаимодействия между {@link NotationConverter} и {@link NotationInterpreter}
 */
public class Calculator {

    private final NotationConverter mConverter;
    private final NotationInterpreter mInter;

    public Calculator(NotationConverter converter, NotationInterpreter inter) {
        mConverter = converter;
        mInter = inter;
    }

    /**
     * Вычисляет значение математического выражения
     * @param exp выражение
     * @return значение
     * @throws RuntimeException в случае ошибки разбора или интерпретации выражения
     */
    public Double calc(String exp) throws RuntimeException {
        Queue<Item> queue = mConverter.convert(exp);
        for (Item current : queue) {
            current.operate(mInter);
        }
        return mInter.poolDigit();
    }

}
