package com.turlir.abakcalc;

import com.turlir.calculator.converter.Printer;
import com.turlir.calculator.converter.Visual;

public class ValueVisualizer implements CalculatorVisual {

    private final Visual mDelegate;

    ValueVisualizer(Visual delegate) {
        mDelegate = delegate;
    }

    @Override
    public void print(Printer chain) {
        mDelegate.print(chain);
    }

    @Override
    public boolean selectionConstraint(int selStart, int selEnd, int length) {
        return false;
    }

    @Override
    public int[] interceptSelection(int a, int b) {
        return new int[0];
    }

    @Override
    public int constraintStart() {
        return 0;
    }

    @Override
    public int constraintEnd() {
        return 0;
    }

    @Override
    public String toString() {
        return mDelegate.toString();
    }
}
