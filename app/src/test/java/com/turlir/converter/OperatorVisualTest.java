package com.turlir.converter;

import android.support.annotation.NonNull;
import android.widget.EditText;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OperatorVisualTest {

    @Test
    public void selectionTestBindingRight() {
        Visual ov = range(1, 4);

        assertFalse(ov.selectionConstraint(1, 9, 9));
        assertTrue(ov.selectionConstraint(2, 9, 9));
        assertTrue(ov.selectionConstraint(3, 9, 9));
        assertFalse(ov.selectionConstraint(4, 9, 9));
    }

    @Test
    public void selectionTestBindingLeft() {
        Visual ov = range(1, 4);

        assertFalse(ov.selectionConstraint(0, 4, 9));
        assertTrue(ov.selectionConstraint(0, 3, 9));
        assertTrue(ov.selectionConstraint(0, 2, 9));
        assertFalse(ov.selectionConstraint(0, 1, 9));

        assertFalse(ov.selectionConstraint(4, 9, 9));
    }

    @Test
    public void cornerCaseTest() {
        Visual ov = range(1, 4);

        assertFalse(ov.selectionConstraint(1, 4, 9));

        assertTrue(ov.selectionConstraint(2, 4, 9));
        assertTrue(ov.selectionConstraint(3, 4, 9));

        assertTrue(ov.selectionConstraint(1, 3, 9));
        assertTrue(ov.selectionConstraint(1, 2, 9));
    }

    @Test
    public void selectionAtTheEnd() {
        Visual ov = range(5, 8);
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
        Visual ov = range(1, 4);

        assertTrue(ov.selectionConstraint(1, 1, 9));
        assertTrue(ov.selectionConstraint(2, 2, 9));
        assertTrue(ov.selectionConstraint(3, 3, 9));
        assertTrue(ov.selectionConstraint(4, 4, 9));
    }

    @Test
    public void singleCursorIntercept() {
        Visual ov = range(1, 4);
        checkCursor(ov, 2, 2, 4, 4);
        checkCursor(ov, 3, 3, 4, 4);
        checkCursor(ov, 1, 1, 1, 1); // never called
    }

    @Test
    public void multiplyCursorInterceptRightBinding() {
        Visual ov = range(1, 4);
        checkCursor(ov, 2, 9, 4, 9);
        checkCursor(ov, 3, 9, 4, 9);
    }

    @Test
    public void multiplyCursorInterceptLeftBinding() {
        Visual ov = range(1, 4);
        checkCursor(ov, 0, 3, 0, 1);
        checkCursor(ov, 0, 2, 0, 1);
    }

    @NonNull
    private static Visual range(int start, int end) {
        Visual ov = new OperatorVisual(" + ");
        Printer p = Mockito.mock(Printer.class);
        Mockito.when(p.length()).thenReturn(start, end);
        ov.print(p);
        return ov;
    }

    private static void checkCursor(Visual ov, int userStart, int userEnd, int a, int b) {
        EditText e = Mockito.mock(EditText.class);
        ov.interceptSelection(e, userStart, userEnd);
        Mockito.verify(e).setSelection(Mockito.eq(a), Mockito.eq(b));
    }

}