package com.turlir.converter;


import android.widget.EditText;

class OperatorVisual implements Visual {

    private final String mToken;
    private int mStart, mEnd;

    OperatorVisual(String token) {
        mToken = token;
    }

    @Override
    public void print(Printer chain) {
        mStart = chain.length();
        chain.append(mToken);
        mEnd = chain.length();
    }

    @Override
    public boolean selectionConstraint(int selStart, int selEnd, int length) {
        boolean safety = mToken.length() > 1 && length >= mEnd;
        boolean union;
        if (selStart != selEnd) {
            union = selStart <= mStart && selEnd < mEnd && selEnd > mStart;
            union = union || selEnd >= mEnd && selStart > mStart && selStart < mEnd;
        } else {
            union = false;
        }
        return safety && union;
    }

    @Override
    public void interceptSelection(EditText editor, int nowS, int nowE) {
        if (belong(nowE, mStart, mEnd)) {
            editor.setSelection(nowS, mStart);
        } else if (belong(nowS, mStart, mEnd)) {
            editor.setSelection(mEnd, nowE);
        }
        else {
            //throw new RuntimeException();
        }
    }

    private static boolean belong(int i, int start, int end) {
        return i > start && i < end;
    }

    @Override
    public int constraintStart() {
        return mStart;
    }

    @Override
    public int constraintEnd() {
        return mEnd;
    }
}
