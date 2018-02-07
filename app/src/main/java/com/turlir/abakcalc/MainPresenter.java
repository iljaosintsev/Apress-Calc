package com.turlir.abakcalc;

import com.turlir.Analyzer;
import com.turlir.Calculator;
import com.turlir.converter.Member;
import com.turlir.converter.Visual;

import java.text.DecimalFormatSymbols;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Queue;

class MainPresenter {

    //private static final String TAG = "Presenter";

    private static final DecimalFormatSymbols FORMAT  = new DecimalFormatSymbols(Locale.getDefault());
    static final String SEPARATOR = String.valueOf(FORMAT.getDecimalSeparator());

    private final Calculator mCalc;
    private final Analyzer mAnalyze;

    private MainView mView;

    MainPresenter(Calculator calc, Analyzer analyze) {
        mCalc = calc;
        mAnalyze = analyze;
    }

    void attach(MainView view) {
        mView = view;
    }

    void detach() {
        mView = null;
    }

    void enter(String s) {
        try {
            calculate(s);
        } catch (EmptyStackException | NoSuchElementException e) {
            //Log.e(TAG, "EmptyStackException при вычислении", e);
            mView.showError(mView.context().getString(R.string.error));

        } catch (Exception e) {
            //Log.e(TAG, "Ошибка при вычислении", e);
            mView.showError(e.getMessage());
        }
    }

    void recalculate(String s) {
        try {
            calculate(s);
        } catch (Exception e) {
            //Log.w(TAG, "Ошибка " + e + " при перерасчете строки " + s);
            //e.printStackTrace();
        }
    }

    private void calculate(String str) throws Exception {
        str = str.replaceAll("\\s+", "").replace(SEPARATOR, ".");

        Queue<Member> q = mAnalyze.analyze(str);
        List<Visual> visual = mAnalyze.display();
        mView.setRepresentation(visual);

        String digit = mCalc.represent(q);
        mView.showResult(digit);
    }

}
