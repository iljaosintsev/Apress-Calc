package com.turlir.calculator.converter;

public interface Visual {

    void print(Printer chain);

    boolean selectionConstraint(int selStart, int selEnd, int length);

    int[] interceptSelection(int nowS, int nowE);

    int constraintStart();

    int constraintEnd();

}
