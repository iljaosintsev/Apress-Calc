package com.turlir.abakcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

import java.util.EmptyStackException;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.edit_text)
    Editor editText;

    @BindView(R.id.tv_result)
    TextView result;

    private Calculator mCalc;
    private Analyzer mAnalyze;

    private final StringBuilder mInputCopy = new StringBuilder();

    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        NotationTranslator conv = new PolishTranslator();
        NotationInterpreter inter = new PolishInterpreter();
        mCalc = new Calculator(conv, inter);
        mAnalyze = new Analyzer(
                new MemberConverter(
                        new MultiOperatorExtractor(
                                new ExpressionPartExtractor()
                        )
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.setText("");
        result.setText("");
    }

    @OnClick({
            R.id.btn_add,
            R.id.btn_minus,
            R.id.btn_multi,
            R.id.btn_div,
            //
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9,
            //
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            //
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            //
            R.id.btn_0,
            R.id.btn_dot,
            R.id.btn_cs,
            R.id.btn_os,
            //
            R.id.btn_clear,
            R.id.btn_enter
    })
    public void keyboardButtonClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add:
                append("+");
                break;
            case R.id.btn_minus:
                append("-"); // унарный минус
                break;
            case R.id.btn_multi:
                append("*");
                break;
            case R.id.btn_div:
                append("/");
                break;
            //
            case R.id.btn_7:
                append("7");
                break;
            case R.id.btn_8:
                append("8");
                break;
            case R.id.btn_9:
                append("9");
                break;
            //
            case R.id.btn_4:
                append("4");
                break;
            case R.id.btn_5:
                append("5");
                break;
            case R.id.btn_6:
                append("6");
                break;
            //
            case R.id.btn_1:
                append("1");
                break;
            case R.id.btn_2:
                append("2");
                break;
            case R.id.btn_3:
                append("3");
                break;
            //
            case R.id.btn_0:
                append("0");
                break;
            case R.id.btn_dot:
                append(".");
                break;
            case R.id.btn_cs:
                append("(");
                break;
            case R.id.btn_os:
                append(")");
                break;
            //
            case R.id.btn_clear:
                clear();
                break;
            case R.id.btn_enter:
                enter();
                break;
        }
    }

    private void append(String s) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start != end) throw new RuntimeException();
        String now = mInputCopy.insert(start, s).toString();
        recalculate(now);
    }

    private void enter() {
        String str = editText.getText().toString();
        try {
            calculate(str);
        } catch (EmptyStackException e) {
            Log.e(TAG, "EmptyStackException при вычислении", e);
            result.setText(R.string.error);
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при вычислении", e);
            result.setText(e.getMessage());
        }
    }

    private void clear() {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start != end) throw new RuntimeException();
        String str = editText.getText().replace(start - 1, start, "").toString();
        recalculate(str);
    }

    private void recalculate(String str) {
        try {
            calculate(str);
        } catch (Exception e) {
            Log.w(TAG, "Ошибка при перерасчете " + e);
        }
    }

    private void calculate(String str) throws Exception {
        Queue<Member> q = mAnalyze.analyze(str);

        List<Visual> v = mAnalyze.display();
        String now = editText.setRepresentation(v);
        mInputCopy.replace(0, mInputCopy.length(), now);

        String res = mCalc.represent(q);
        Log.d(TAG, "Результат " + res);
        String strRes = getString(R.string.result, res);
        result.setText(strRes);
    }

}
