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
    public boolean selectionConstraint(int selEnd) {
        return mToken.length() > 1 && selEnd > mStart && selEnd < mEnd;
    }

    @Override
    public void interceptSelection(EditText editor) {
        int selStart = editor.getSelectionStart();
        int selEnd = editor.getSelectionEnd();
        final int s;
        if (selStart < mEnd)
            s = Math.min(selStart, mStart);
        else
            s = selStart;
        final int e = Math.max(selEnd, mEnd);
        editor.setSelection(s, e);
    }
}
