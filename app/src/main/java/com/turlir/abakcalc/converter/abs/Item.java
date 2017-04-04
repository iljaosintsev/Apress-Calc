package com.turlir.abakcalc.converter.abs;


public interface Item {

    void operate(NotationInterpreter visitor) throws RuntimeException;

}
