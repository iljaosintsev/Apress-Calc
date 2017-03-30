package com.turlir.abakcalc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final DecimalFormat DF = new DecimalFormat("#.###"); // result format

    @BindView(R.id.edit_text)
    EditText editText;

    @BindView(R.id.tv_result)
    TextView result;

    private Calc mCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCalc = new Calc();
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
    public void clickAdd(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add:
                append(" + ");
                break;
            case R.id.btn_minus:
                append(" - ");
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
        return true;
    }

    private void append(String s) {
        String n = editText.getText() + s;
        editText.setText(n);
    }

    private void clearOne() {
        Editable origin = editText.getText();
        if (origin.length() > 0) {
            int del; // удаляем 1 или 2 элемента по очереди
            if (origin.length() % 2 != 0) {
                del = 1;
            } else {
                del = 2;
            }
            CharSequence n = origin.subSequence(0, origin.length() - del);
            editText.setText(n);
            if (n.length() == 0) {
                result.setText("");
            }
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

}
