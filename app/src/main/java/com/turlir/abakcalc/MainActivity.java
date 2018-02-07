package com.turlir.abakcalc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.turlir.converter.Visual;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";

    @BindView(R.id.btn_dot)
    Button dot;

    @BindView(R.id.edit_text)
    Editor editText;

    @BindView(R.id.tv_result)
    TextView result;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter();
        mPresenter.attach(this);
        dot.setText(MainPresenter.SEPARATOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.setText("");
        result.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
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
    void keyboardButtonClick(View view) {
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
                append(MainPresenter.SEPARATOR);
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

    @Override
    public void showResult(String digit) {
        result.setText(digit);
    }

    @Override
    public void setRepresentation(List<Visual> v) {
        editText.setRepresentation(v);
    }

    @Override
    public void showError(String error) {
        result.setText(error);
    }

    @Override
    public Context context() {
        return getApplicationContext();
    }

    private void append(String s) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start != end) throw new RuntimeException();
        String now = editText.insertSymbol(start, s);
        mPresenter.recalculate(now);
    }

    private void enter() {
        String str = editText.getText().toString();
        mPresenter.enter(str);
    }

    private void clear() {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start != end) throw new RuntimeException();
        String str = editText.removeSymbol(start);
        mPresenter.recalculate(str);
    }

}
