package com.turlir.abakcalc

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnLongClick
import com.turlir.calculator.Analyzer
import com.turlir.calculator.Calculator
import com.turlir.calculator.converter.Visual
import com.turlir.calculator.interpreter.PolishInterpreter
import com.turlir.calculator.translator.PolishTranslator
import java.net.URLEncoder


class MainActivity : AppCompatActivity(), MainView {

    @BindView(R.id.btn_dot)
    lateinit var dot: Button

    @BindView(R.id.btn_enter)
    lateinit var en: Button

    @BindView(R.id.edit_text)
    lateinit var editText: Editor

    @BindView(R.id.tv_result)
    lateinit var result: TextView

    @BindView(R.id.list_notation)
    lateinit var notation: RecyclerView

    private lateinit var mPresenter: MainPresenter
    private lateinit var mAdapter: NotationRecyclerAdapter

    override fun onCreate(save: Bundle?) {
        super.onCreate(save)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        mAdapter = NotationRecyclerAdapter(this)
        notation.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpaceDecorator(this@MainActivity, R.dimen.notation_item_padding, R.dimen.zero))
            adapter = mAdapter
        }

        val analyzer = Analyzer()
        val translator = PolishTranslator()
        val interpreter = PolishInterpreter()
        val calculator = Calculator(analyzer, translator, interpreter)
        mPresenter = MainPresenter(calculator)
        mPresenter.attach(this)

        dot.text = mPresenter.separator

        val drawables = en.compoundDrawablesRelative
        if (drawables[0] != null) {
            val wrap = DrawableCompat.wrap(drawables[0])
            DrawableCompat.setTint(wrap, Color.WHITE)
        }
    }

    override fun onRestoreInstanceState(saved: Bundle?) {
        super.onRestoreInstanceState(saved)
        if (saved != null) enter()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
    }

    @OnClick(R.id.btn_add, R.id.btn_minus, R.id.btn_multi, R.id.btn_div,
            //
            R.id.btn_7, R.id.btn_8, R.id.btn_9,
            //
            R.id.btn_4, R.id.btn_5, R.id.btn_6,
            //
            R.id.btn_1, R.id.btn_2, R.id.btn_3,
            //
            R.id.btn_0, R.id.btn_dot, R.id.btn_cs, R.id.btn_os,
            //
            R.id.btn_clear, R.id.btn_enter)
    fun keyboardButtonClick(view: View) {
        when (view.id) {
            R.id.btn_add -> append("+")
            R.id.btn_minus -> append("-") // унарный минус
            R.id.btn_multi -> append("*")
            R.id.btn_div -> append("/")
            //
            R.id.btn_7 -> append("7")
            R.id.btn_8 -> append("8")
            R.id.btn_9 -> append("9")
            //
            R.id.btn_4 -> append("4")
            R.id.btn_5 -> append("5")
            R.id.btn_6 -> append("6")
            //
            R.id.btn_1 -> append("1")
            R.id.btn_2 -> append("2")
            R.id.btn_3 -> append("3")
            //
            R.id.btn_0 -> append("0")
            R.id.btn_dot -> append(mPresenter.separator)
            R.id.btn_cs -> append("(")
            R.id.btn_os -> append(")")
            //
            R.id.btn_clear -> clear()
            R.id.btn_enter -> enter()
        }
    }

    @OnLongClick(R.id.btn_clear)
    fun clearAll(view: View): Boolean {
        resetToEmpty()
        return true
    }

    @OnLongClick(R.id.btn_enter)
    fun openWolfram(view: View): Boolean {
        val i = Intent(Intent.ACTION_VIEW)
        val param = URLEncoder.encode(editText.text.toString(), "UTF-8")
        val url = "http://m.wolframalpha.com/input/?i=$param"
        i.data = Uri.parse(url)
        startActivity(i)
        return true
    }

    override fun showResult(digit: String) {
        result.text = digit
    }

    override fun resetToEmpty() {
        editText.setText("")
        showError("")
        setNotation(emptyList())
    }

    override fun setRepresentation(v: List<CalculatorVisual>) {
        editText.setRepresentation(v)
    }

    override fun setNotation(v: List<Visual>) {
        mAdapter.setItems(v)
        mAdapter.notifyDataSetChanged()
    }

    override fun showError(error: String) {
        result.text = error
    }

    override val context: Context
        get() = applicationContext

    private fun append(s: String) {
        val start = editText.selectionStart
        val end = editText.selectionEnd
        if (start != end) throw RuntimeException()
        val now = editText.insertSymbol(start, s)
        mPresenter.recalculate(now)
    }

    private fun enter() {
        val str = editText.text.toString()
        mPresenter.enter(str)
    }

    private fun clear() {
        val start = editText.selectionStart
        val end = editText.selectionEnd
        if (start != end) throw RuntimeException()
        val str = editText.removeSymbol(start)
        mPresenter.recalculate(str)
    }

}
