package com.turlir.abakcalc;

import android.content.Context;
import android.util.AttributeSet;

import com.turlir.converter.Printer;
import com.turlir.converter.Visual;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Editor extends android.support.v7.widget.AppCompatEditText {

    private List<Visual> mViews;

    private final Printer mPrinter;
    private final StringBuilder mCopy = new StringBuilder();

    public Editor(Context context) {
        this(context, null);
    }

    public Editor(Context context, AttributeSet attrs) {
        super(context, attrs);
        setShowSoftInputOnFocus(false);
        mViews = Collections.emptyList();

        DecimalFormat format = new DecimalFormat();
        DecimalFormatSymbols settings = new DecimalFormatSymbols(Locale.getDefault());
        format.setDecimalFormatSymbols(settings);
        format.setMaximumFractionDigits(340); // see doc DecimalFormat
        format.setMinimumIntegerDigits(1);

        mPrinter = new DirectPrinter(format);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (mCopy != null) {
            mCopy.replace(0, mCopy.length(), text.toString());
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (mViews != null) {
            int l = getText().length();
            for (Visual item : mViews) {
                if (item.selectionConstraint(selStart, selEnd, l)) {
                    item.interceptSelection(this, selStart, selEnd);
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
    }

    public String removeSymbol(int index) {
        int length = getText().length();
        if (length > 0 && index > 0) {
            for (Visual item : mViews) {
                if (item.selectionConstraint(index, index, length)) {
                    int s = item.constraintStart();
                    int e = item.constraintEnd();
                    return getText().replace(s, e, "").toString();
                }
            }
            return getText().replace(index - 1, index, "").toString();

        } else if (index == 0) {
            return getText().toString();

        } else {
            return "";
        }
    }

    public String insertSymbol(int index, String s) {
        mCopy.insert(index, s);
        return mCopy.toString();
    }
}
