package com.turlir.abakcalc;

import com.turlir.Analyzer;
import com.turlir.Calculator;
import com.turlir.converter.Member;
import com.turlir.converter.MemberConverter;
import com.turlir.converter.Visual;
import com.turlir.extractors.ExpressionPartExtractor;
import com.turlir.extractors.MultiOperatorExtractor;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.interpreter.PolishInterpreter;
import com.turlir.translator.NotationTranslator;
import com.turlir.translator.PolishTranslator;

import java.lang.ref.WeakReference;
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

    private WeakReference<MainView> mView;

    MainPresenter() {
        mView = new WeakReference<>(null);

        NotationTranslator conv = new PolishTranslator();
        NotationInterpreter inter = new PolishInterpreter();
        mCalc = new Calculator(conv, inter);
        mAnalyze = new Analyzer(new MemberConverter(new MultiOperatorExtractor(new ExpressionPartExtractor())));
    }

    void attach(MainView view) {
        mView = new WeakReference<>(view);
    }

    void detach() {
        mView.clear();
    }

    void enter(String s) {
        try {
            calculate(s);
        } catch (EmptyStackException | NoSuchElementException e) {
            //Log.e(TAG, "EmptyStackException при вычислении", e);
            MainView v = mView.get();
            v.showError(v.context().getString(R.string.error));

        } catch (Exception e) {
            //Log.e(TAG, "Ошибка при вычислении", e);
            mView.get().showError(e.getMessage());
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
        MainView view = mView.get();

        Queue<Member> q = mAnalyze.analyze(str);
        List<Visual> visual = mAnalyze.display();
        view.setRepresentation(visual);

        String digit = mCalc.represent(q);
        view.showResult(digit);
    }

}
