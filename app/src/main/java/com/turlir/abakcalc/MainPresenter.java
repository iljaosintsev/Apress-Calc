package com.turlir.abakcalc;

import com.turlir.calculator.Analyzer;
import com.turlir.calculator.Calculator;
import com.turlir.calculator.converter.Member;
import com.turlir.calculator.converter.Visual;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Queue;

class MainPresenter {

    private static final DecimalFormat DF = new DecimalFormat(""); // формат результата
    private static final DecimalFormatSymbols FORMAT = new DecimalFormatSymbols(Locale.getDefault());

    static {
        DF.setDecimalFormatSymbols(FORMAT);
        DF.setMaximumFractionDigits(12);
        DF.setMinimumIntegerDigits(1);
    }

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
            showNotation();

        } catch (EmptyStackException | NoSuchElementException e) {
            mView.showError(mView.context().getString(R.string.error));

            mView.setNotation(Collections.<Visual>emptyList());

        } catch (Exception e) {
            mView.showError(e.getMessage());

            mView.setNotation(Collections.<Visual>emptyList());
        }
    }

    private void showNotation() {
        Queue<Member> row = mCalc.translated();
        List<Visual> now = new ArrayList<>(mCalc.size());
        for (Member item : row) now.add(item.view());
        mView.setNotation(now);
    }

    void recalculate(String s) {
        if (s == null || s.isEmpty()) {
            mView.resetToEmpty();
            return;
        }

        try {
            calculate(s);
        } catch (Exception ignored) {

        }
    }

    private void calculate(String str) throws Exception {
        str = str.replaceAll("\\s+", "").replace(SEPARATOR, ".");
        List<Member> copy = mAnalyze.slice(str);
        List<Visual> visual = interceptPrimary(copy);
        mView.setRepresentation(visual);

        BigDecimal digit = mCalc.calc(copy.iterator());
        mView.showResult(DF.format(digit));
    }

    private static List<Visual> interceptPrimary(Collection<Member> sequence) {
        List<Visual> views = new ArrayList<>(sequence.size());
        for (Member member : sequence) {
            views.add(member.view());
        }
        return views;
    }

}
