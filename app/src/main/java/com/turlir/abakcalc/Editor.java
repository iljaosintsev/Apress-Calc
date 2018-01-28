package com.turlir.abakcalc;

import android.content.Context;
import android.util.AttributeSet;

public class Editor extends android.support.v7.widget.AppCompatEditText {

    public Editor(Context context) {
        super(context);
    }

    public Editor(Context context, AttributeSet attrs) {
        super(context, attrs);
        setShowSoftInputOnFocus(false);
    }
}
