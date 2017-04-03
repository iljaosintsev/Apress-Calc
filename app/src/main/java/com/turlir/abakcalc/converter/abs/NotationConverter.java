package com.turlir.abakcalc.converter.abs;


import java.util.Queue;

public interface NotationConverter {

    Queue<Item> convert(String input) throws Exception;

}
