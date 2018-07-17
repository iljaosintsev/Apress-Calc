package com.turlir.abakcalc;

import com.turlir.calculator.converter.Visual;

public interface CalculatorVisual extends Visual {

    boolean selectionConstraint(int selStart, int selEnd, int length);

    int[] interceptSelection(int nowS, int nowE);

    int constraintStart();

    int constraintEnd();
}
