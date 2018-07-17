package com.turlir.abakcalc;

import com.turlir.calculator.member.Operators;
import com.turlir.calculator.converter.Printer;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OperatorVisualizerTest {

    private CalculatorVisual ov;

    @Test
    public void selectionTestBindingRight() {
        ov = range(1, 4);

        assertFalse(ov.selectionConstraint(1, 9, 9));
        assertTrue(ov.selectionConstraint(2, 9, 9));
        assertTrue(ov.selectionConstraint(3, 9, 9));
        assertFalse(ov.selectionConstraint(4, 9, 9));
    }

    @Test
    public void selectionTestBindingLeft() {
        ov = range(1, 4);

        assertFalse(ov.selectionConstraint(0, 4, 9));
        assertTrue(ov.selectionConstraint(0, 3, 9));
        assertTrue(ov.selectionConstraint(0, 2, 9));
        assertFalse(ov.selectionConstraint(0, 1, 9));

        assertFalse(ov.selectionConstraint(4, 9, 9));
    }

    @Test
    public void cornerCaseTest() {
        ov = range(1, 4);

        assertFalse(ov.selectionConstraint(1, 4, 9));

        assertTrue(ov.selectionConstraint(2, 4, 9));
        assertTrue(ov.selectionConstraint(3, 4, 9));

        assertTrue(ov.selectionConstraint(1, 3, 9));
        assertTrue(ov.selectionConstraint(1, 2, 9));
    }

    @Test
    public void selectionAtTheEnd() {
        ov = range(5, 8);
        // binding right
        assertFalse(ov.selectionConstraint(5, 9, 9));
        assertTrue(ov.selectionConstraint(6, 9, 9));
        assertTrue(ov.selectionConstraint(7, 9, 9));
        assertFalse(ov.selectionConstraint(8, 9, 9));
        // binding left
        assertFalse(ov.selectionConstraint(0, 5, 9));
        assertTrue(ov.selectionConstraint(0, 6, 9));
        assertTrue(ov.selectionConstraint(0, 7, 9));
        assertFalse(ov.selectionConstraint(0, 8, 9));
    }

    @Test
    public void singleCursor() {
        ov = range(1, 4);

        assertTrue(ov.selectionConstraint(1, 1, 9));
        assertTrue(ov.selectionConstraint(2, 2, 9));
        assertTrue(ov.selectionConstraint(3, 3, 9));
        assertTrue(ov.selectionConstraint(4, 4, 9));
    }

    @Test
    public void singleCursorIntercept() {
        ov = range(1, 4);
        checkCursor(ov, 2, 2, 4, 4);
        checkCursor(ov, 3, 3, 4, 4);
        checkCursor(ov, 1, 1, 1, 1); // never called
    }

    @Test
    public void multiplyCursorInterceptRightBinding() {
        ov = range(1, 4);
        checkCursor(ov, 2, 9, 4, 9);
        checkCursor(ov, 3, 9, 4, 9);
    }

    @Test
    public void multiplyCursorInterceptLeftBinding() {
        ov = range(1, 4);
        checkCursor(ov, 0, 3, 0, 1);
        checkCursor(ov, 0, 2, 0, 1);
    }

    private static OperatorVisualizer range(int start, int end) {
        OperatorVisualizer ov = new OperatorVisualizer(Operators.find("+").view());
        Printer p = Mockito.mock(Printer.class);
        Mockito.when(p.length()).thenReturn(start, end);
        ov.print(p);
        return ov;
    }

    private static void checkCursor(CalculatorVisual ov, int userStart, int userEnd, int a, int b) {
        int[] ab = ov.interceptSelection(userStart, userEnd);
        assertEquals(ab[0], a);
        assertEquals(ab[1], b);
    }

}