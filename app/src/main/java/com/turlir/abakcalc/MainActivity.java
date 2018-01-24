package com.turlir.abakcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.turlir.Calculator;
import com.turlir.converter.MemberConverter;
import com.turlir.extractors.ExpressionPartExtractor;
import com.turlir.extractors.MultiOperatorExtractor;
import com.turlir.interpreter.NotationInterpreter;
import com.turlir.interpreter.PolishInterpreter;
import com.turlir.translator.NotationTranslator;
import com.turlir.translator.PolishTranslator;

import java.text.DecimalFormat;
import java.util.EmptyStackException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final DecimalFormat DF = new DecimalFormat("#.###"); // формат результата

    @BindView(R.id.edit_text)
    EditText editText;

    @BindView(R.id.tv_result)
    TextView result;

    private Calculator mCalc;

    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        NotationTranslator conv = new PolishTranslator();
        NotationInterpreter inter = new PolishInterpreter();
        mCalc = new Calculator(new MemberConverter(
                new MultiOperatorExtractor(
                        new ExpressionPartExtractor()
                )
        ), conv, inter);
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
            R.id.btn_enter
    })
    public void keyboardButtonClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add:
                append(" + ");
                break;
            case R.id.btn_minus:
                if (isLastOperator()) {
                    append("-"); // унарный минус
                } else {
                    append(" - ");
                }
                break;
            case R.id.btn_multi:
                append(" * ");
                break;
            case R.id.btn_div:
                append(" / ");
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
                append("( ");
                break;
            case R.id.btn_os:
                append(" )");
                break;
            //
            case R.id.btn_enter:
                enter();
                break;
        }
    }

    private void append(String s) {
        String n = editText.getText() + s;
        editText.setText(n);
    }

    private void enter() {
        String str = editText.getText().toString();
        if (str.length() > 2) {
            try {
                Double calc = mCalc.calc(str);
                Log.d(TAG, "Результат " + calc);
                String strRes = getString(R.string.result, DF.format(calc));
                result.setText(strRes);
            } catch (EmptyStackException e) {
                Log.e(TAG, "EmptyStackException при вычислении", e);
                result.setText(R.string.error);
            } catch (Exception e) {
                Log.e(TAG, "Ошибка при вычислении", e);
                result.setText(e.getMessage());
            }
        }
    }

    // унарный минус может стоять в следующих позициях
    private boolean isLastOperator() {
        return false;
    }

}
