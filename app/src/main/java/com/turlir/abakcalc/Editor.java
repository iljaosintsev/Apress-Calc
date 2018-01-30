package com.turlir.abakcalc;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;

import com.turlir.converter.Printer;
import com.turlir.converter.Visual;

import java.util.Collections;
import java.util.List;

public class Editor extends android.support.v7.widget.AppCompatEditText {

    private List<Visual> mViews;

    private final Printer mPrinter = new DirectPrinter();

    public Editor(Context context) {
        this(context, null);
    }

    public Editor(Context context, AttributeSet attrs) {
        super(context, attrs);
        setShowSoftInputOnFocus(false);
        mViews = Collections.emptyList();
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        Log.d("Editor", "onSelectionChanged " + selStart + " " + selEnd);
        if (mViews != null /*selStart != 0 || selStart != selEnd*/) {
            int l = getText().length();
            for (Visual item : mViews) {
                if (item.selectionConstraint(selEnd, l)) {
                    item.interceptSelection(this);
                    return;
                }
            }
        }
    }

    public void setRepresentation(List<Visual> views) {
        for (Visual view : views) {
            view.print(mPrinter);
        }
        String s = mPrinter.toString();
        mPrinter.reset();
        mViews = views;
        int ss = getSelectionStart();
        int oldLength = getText().length();
        setText(s);
        setSelection(ss + (s.length() - oldLength));
        //return s;
    }

    public String removeSymbol(int index) {
        int length = getText().length();
        if (length > 0 && index > 0) {
            for (Visual item : mViews) {
                if (item.selectionConstraint(index, length)) {
                    int s = item.constraintStart();
                    int e = item.constraintEnd();
                    return getText().replace(s, e, "").toString();
                }
            }
            return getText().replace(index - 1, index, "").toString();
        }
        return "";
    }

    public String insertSymbol(int index, String s) {
        Editable txt = getEditableText();
        CharSequence copy = txt.subSequence(0, txt.length());
        String maybe = txt.insert(index, s).toString();
        txt.replace(0, txt.length(), copy);
        return maybe;
    }
}
