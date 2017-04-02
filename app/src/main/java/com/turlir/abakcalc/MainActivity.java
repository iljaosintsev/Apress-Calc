package com.turlir.abakcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.turlir.abakcalc.converter.Calculator;
import com.turlir.abakcalc.converter.PolishConverter;
import com.turlir.abakcalc.converter.PolishInterpreter;
import com.turlir.abakcalc.converter.abs.NotationConverter;
import com.turlir.abakcalc.converter.abs.NotationInterpreter;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final DecimalFormat DF = new DecimalFormat("#.###"); // формат результата
    private static final String BUNDLE_QUEUE = "BUNDLE_QUEUE";

    @BindView(R.id.edit_text)
    EditText editText;

    @BindView(R.id.tv_result)
    TextView result;

    private Calculator mCalc;
    private LinkedList<String> mInputQueue; // очередь вставок для удаления

    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        NotationConverter conv = new PolishConverter();
        NotationInterpreter inter = new PolishInterpreter();
        mCalc = new Calculator(conv, inter);

        // восстановление состояния
        if (save != null) {
            restore(save);
        } else {
            mInputQueue = new LinkedList<>();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String[] array = mInputQueue.toArray(new String[mInputQueue.size()]);
        outState.putStringArray(BUNDLE_QUEUE, array);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saved) {
        super.onRestoreInstanceState(saved);
        restore(saved);
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
            case R.id.btn_clear:
                clearOne();
                break;
            case R.id.btn_enter:
                enter();
                break;
        }
    }

    @OnLongClick(R.id.btn_clear)
    public boolean clearAll() {
        editText.setText("");
        result.setText("");
        mInputQueue.clear();
        return true;
    }

    private void restore(Bundle save) {
        String[] array = save.getStringArray(BUNDLE_QUEUE);
        if (array != null) {
            mInputQueue = new LinkedList<>(Arrays.asList(array));
        }
    }

    private void append(String s) {
        String n = editText.getText() + s;
        editText.setText(n);
        mInputQueue.push(s);
    }

    private void clearOne() {
        int l = editText.length();
        if (l > 0) {
            String lastInput = mInputQueue.pop();
            int del = lastInput.length();
            Editable origin = editText.getText();
            CharSequence n = origin.subSequence(0, l - del);
            editText.setText(n);
            result.setText(""); // сбрасываем результата
        }
    }

    private void enter() {
        String str = editText.getText().toString();
        if (str.length() > 2) {
            try {
                Double calc = mCalc.calc(str);
                Log.d(TAG, "Результат " + calc);
                String strRes = getString(R.string.result, DF.format(calc));
                result.setText(strRes);
            } catch (Exception e) {
                Log.e(TAG, "Ошибка при вычислении", e);
                result.setText(e.getMessage());
            }
        }
    }

    // унарный минус может стоять в следующих позициях
    private boolean isLastOperator() {
        String lastInput = mInputQueue.peek();
        return lastInput == null // в начале выражения
                || lastInput.equals(" + ") // операции
                || lastInput.equals(" - ")
                || lastInput.equals(" * ")
                || lastInput.equals(" / ")
                || lastInput.equals("( "); // открывающаяся скобка
    }

}
