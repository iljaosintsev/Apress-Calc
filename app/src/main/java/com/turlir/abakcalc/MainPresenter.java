package com.turlir.abakcalc;

import com.turlir.Analyzer;
import com.turlir.Calculator;
import com.turlir.converter.Member;
import com.turlir.converter.Visual;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Queue;

class MainPresenter {

    //private static final String TAG = "Presenter";

    private static final DecimalFormat DF = new DecimalFormat("#.###"); // формат результата

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
        if (s == null || s.isEmpty()) {
            mView.resetToEmpty();
            return;
        }

        try {
            calculate(s);
            Queue<Member> row = mCalc.translated();
            List<Visual> now = new ArrayList<>(mCalc.size());
            for (Member item : row) now.add(item.view());
            mView.setNotation(now);

        } catch (EmptyStackException | NoSuchElementException e) {
            //Log.e(TAG, "EmptyStackException при вычислении", e);
            mView.showError(mView.context().getString(R.string.error));

            mView.setNotation(Collections.<Visual>emptyList());

        } catch (Exception e) {
            //Log.e(TAG, "Ошибка при вычислении", e);
            mView.showError(e.getMessage());

            mView.setNotation(Collections.<Visual>emptyList());
        }
    }

    void recalculate(String s) {
        if (s == null || s.isEmpty()) {
            mView.resetToEmpty();
            return;
        }

        try {
            calculate(s);
        } catch (Exception e) {
            //Log.w(TAG, "Ошибка " + e + " при перерасчете строки " + s);
            //e.printStackTrace();
        }
    }

    private void calculate(String str) throws Exception {
        str = str.replaceAll("\\s+", "").replace(SEPARATOR, ".");
        List<Member> copy = mAnalyze.slice(str);
        List<Visual> visual = mAnalyze.display(copy);
        mView.setRepresentation(visual);

        double digit = mCalc.calc(copy.iterator());
        mView.showResult(DF.format(digit));
    }

}
