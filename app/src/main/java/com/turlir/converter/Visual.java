package com.turlir.converter;

import android.widget.EditText;

public interface Visual {

    void print(Printer chain);

    boolean selectionConstraint(int selEnd);

    void interceptSelection(EditText editor);
}
