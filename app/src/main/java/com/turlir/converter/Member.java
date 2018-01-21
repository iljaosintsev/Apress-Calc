package com.turlir.converter;

import com.turlir.abakcalc.converter.abs.NotationInterpreter;

public interface Member {

    boolean operand();

    void process(NotationInterpreter interpreter);

}
