package com.turlir.converter;

import com.turlir.interpreter.NotationInterpreter;

public interface Member {

    boolean operand();

    void process(NotationInterpreter interpreter);

    Visual view();

}
