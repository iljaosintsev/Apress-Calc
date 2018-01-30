package com.turlir.converter;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OperatorVisualTest {

    @Test
    public void selectionTestBindingRight() {
        Visual ov = new OperatorVisual(" + ");
        Printer p = Mockito.mock(Printer.class);
        Mockito.when(p.length()).thenReturn(1, 4);
        ov.print(p);

        assertFalse(ov.selectionConstraint(1, 9, 9));
        assertTrue(ov.selectionConstraint(2, 9, 9));
        assertTrue(ov.selectionConstraint(3, 9, 9));
        assertFalse(ov.selectionConstraint(4, 9, 9));
    }

    @Test
    public void selectionTestBindingLeft() {
        Visual ov = new OperatorVisual(" + ");
        Printer p = Mockito.mock(Printer.class);
        Mockito.when(p.length()).thenReturn(1, 4);
        ov.print(p);

        assertFalse(ov.selectionConstraint(0, 4, 9));
        assertTrue(ov.selectionConstraint(0, 3, 9));
        assertTrue(ov.selectionConstraint(0, 2, 9));
        assertFalse(ov.selectionConstraint(0, 1, 9));

        assertFalse(ov.selectionConstraint(4, 9, 9));
    }

    @Test
    public void cornerCaseTest() {
        Visual ov = new OperatorVisual(" + ");
        Printer p = Mockito.mock(Printer.class);
        Mockito.when(p.length()).thenReturn(1, 4);
        ov.print(p);

        assertFalse(ov.selectionConstraint(1, 4, 9));

        assertTrue(ov.selectionConstraint(2, 4, 9));
        assertTrue(ov.selectionConstraint(3, 4, 9));

        assertTrue(ov.selectionConstraint(1, 3, 9));
        assertTrue(ov.selectionConstraint(1, 2, 9));
    }

    @Test
    public void selectionAtTheEnd() {
        Visual ov = new OperatorVisual(" + ");
        Printer p = Mockito.mock(Printer.class);
        Mockito.when(p.length()).thenReturn(5, 8);
        ov.print(p);
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

}