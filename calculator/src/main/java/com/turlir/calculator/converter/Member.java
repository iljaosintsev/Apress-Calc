package com.turlir.calculator.converter;

import com.turlir.calculator.interpreter.NotationInterpreter;

public interface Member {

    boolean operand();

    void process(NotationInterpreter interpreter);

    Visual view();

}
