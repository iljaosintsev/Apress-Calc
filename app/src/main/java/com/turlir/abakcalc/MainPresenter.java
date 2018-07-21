package com.turlir.abakcalc;

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

    private MainView mView;

    MainPresenter(Calculator calc) {
        mCalc = calc;
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
            BigDecimal result = calculate(s);
            showRepresentation(result);
            showNotation();

        } catch (EmptyStackException | NoSuchElementException e) {
            mView.showError(mView.context().getString(R.string.error));

            mView.setNotation(Collections.<Visual>emptyList());

        } catch (Exception e) {
            mView.showError(e.getMessage());

            mView.setNotation(Collections.<Visual>emptyList());
        }
    }

    private void showRepresentation(BigDecimal result) {
        mView.showResult(DF.format(result));
        showRepresentation();
    }

    private void showRepresentation() {
        List<Member> copy = mCalc.direct();
        List<CalculatorVisual> visual = interceptDirect(copy);
        mView.setRepresentation(visual);
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
            BigDecimal result = calculate(s);
            showRepresentation(result);
        } catch (Exception ignored) {
            showRepresentation();
        }
    }

    private  BigDecimal calculate(String str) throws Exception {
        str = str.replace(SEPARATOR, ".");
        BigDecimal digit = mCalc.calc(str);
        return digit;
    }

    private static List<CalculatorVisual> interceptDirect(Collection<Member> sequence) {
        List<CalculatorVisual> views = new ArrayList<>(sequence.size());
        for (Member member : sequence) {
            Visual view = member.view();
            if (member.operand()) {
                views.add(new ValueVisualizer(view));
            } else {
                views.add(new OperatorVisualizer(view));
            }
        }
        return views;
    }

}
