package com.turlir.converter;

import android.widget.EditText;

import com.turlir.interpreter.NotationInterpreter;

public abstract class Parts {

    public static final Member OS = new Part("(", 1) {

        private OperatorVisual mVisual = new OperatorVisual("(");

        @Override
        public void process(NotationInterpreter interpreter) {
            //
        }
        @Override
        public Visual view() {
            return mVisual;
        }
    };

    public static final Part CS = new Part(")", 1) {

        private Visual mVisual = new OperatorVisual(")");

        @Override
        public void process(NotationInterpreter interpreter) {
            //
        }
        @Override
        public Visual view() {
            return mVisual;
        }
    };

    static final Member PLUS = new Part("+", 2) {

        private Visual mVisual = new OperatorVisual(" + ");

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(a + b);
        }
        @Override
        public Visual view() {
            return mVisual;
        }
    };

    static final Member MINUS = new Part("-", 2) {

        private Visual mVisual = new OperatorVisual(" - ");

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(b - a);
        }
        @Override
        public Visual view() {
            return mVisual;
        }
    };

    static final Member MULTI = new Part("*", 3) {

        private Visual mVisual = new OperatorVisual(" * ");

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(a * b);
        }
        @Override
        public Visual view() {
            return mVisual;
        }
    };

    static final Member DIV = new Part("/", 3) {

        private Visual mVisual = new OperatorVisual(" / ");

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            double b = interpreter.poolDigit();
            interpreter.pushDigit(b / a);
        }
        @Override
        public Visual view() {
            return mVisual;
        }
    };

    static final Member UNARY_MINUS = new Part("-", 4) {

        private Visual mVisual = new OperatorVisual("-");

        @Override
        public void process(NotationInterpreter interpreter) {
            double a = interpreter.poolDigit();
            interpreter.pushDigit(-a);
        }
        @Override
        public Visual view() {
            return mVisual;
        }
    };

    static Member find(String token) {
        switch (token) {
            case "*":
                return MULTI;
            case "/":
                return DIV;
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "(":
                return OS;
            case ")":
                return CS;
            default:
                throw new IllegalArgumentException("Операция не доступна");
        }
    }

    private static class OperatorVisual implements Visual {

        private final String mToken;
        private int mStart, mEnd;

        private OperatorVisual(String token) {
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

}
