package com.turlir.abakcalc

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.widget.TextView
import com.turlir.calculator.converter.Printer
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class Editor
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null) : AppCompatEditText(context, attrs) {

    private var mViews: List<CalculatorVisual>? = emptyList()

    private val mPrinter: Printer
    private val mCopy: StringBuilder? = StringBuilder()

    init {
        showSoftInputOnFocus = false

        val format = DecimalFormat()
        format.apply {
            decimalFormatSymbols = DecimalFormatSymbols(Locale.getDefault())
            maximumFractionDigits = 340 // see doc DecimalFormat
            minimumIntegerDigits = 1
        }
        mPrinter = DirectPrinter(format)
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        super.setText(text, type)
        mCopy?.replace(0, mCopy.length, text.toString())
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if(mViews != null) {
            val l = text.length
            for (item in mViews!!) {
                if (item.selectionConstraint(selStart, selEnd, l)) {
                    val (a, b) = item.interceptSelection(selStart, selEnd)
                    setSelection(a, b)
                    return
                }
            }
        }
    }

    fun setRepresentation(views: List<CalculatorVisual>) {
        for (view in views) {
            view.print(mPrinter)
        }
        val s = mPrinter.toString()
        mPrinter.reset()
        mViews = views
        val ss = selectionStart
        val oldLength = text.length
        setText(s)
        setSelection(ss + (s.length - oldLength))
    }

    fun removeSymbol(index: Int): String {
        val length = text.length
        if (length > 0 && index > 0) {
            for (item in mViews!!) {
                if (item.selectionConstraint(index, index, length)) {
                    val s = item.constraintStart()
                    val e = item.constraintEnd()
                    return text.replace(s, e, "").toString()
                }
            }
            return text.replace(index - 1, index, "").toString()

        } else if (index == 0) {
            return text.toString()

        } else {
            return ""
        }
    }

    fun insertSymbol(index: Int, s: String): String {
        mCopy?.insert(index, s)
        return mCopy.toString()
    }
}
