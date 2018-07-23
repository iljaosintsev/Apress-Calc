package com.turlir.abakcalc

import com.turlir.calculator.Calculator
import com.turlir.calculator.converter.Member
import com.turlir.calculator.converter.Visual
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MainPresenter(private val mCalc: Calculator) {

    val separator: String

    private val mFormat = DecimalFormat("") // формат результата

    private var mView: MainView? = null

    init {
        val setting = DecimalFormatSymbols(Locale.getDefault())
        mFormat.apply {
            decimalFormatSymbols = setting
            maximumFractionDigits = 12
            minimumIntegerDigits = 1
        }
        separator = setting.decimalSeparator.toString()
    }

    fun attach(view: MainView) {
        mView = view
    }

    fun detach() {
        mView = null
    }

    fun enter(s: String?) {

        fun enter_(it: MainView) {
            if (s == null || s.isEmpty()) {
                it.resetToEmpty()
                return
            }

            try {
                calculate(s, true)

            } catch (e: EmptyStackException) {
                it.showError(it.context.getString(R.string.error))

            } catch (e: NoSuchElementException) {
                it.showError(it.context.getString(R.string.error))

            } catch (e: Exception) {
                it.showError(e.message!!)

            } finally {
                it.setNotation(emptyList())
            }
        }

        mView?.let { enter_(it) }
    }

    fun recalculate(s: String?) {
        if (s == null || s.isEmpty()) {
            mView?.resetToEmpty()
            return
        }
        try {
            calculate(s, false)
        } catch (ignored: Exception) {
            //
        }
    }

    @Throws(Exception::class)
    private fun calculate(str: String, shouldTranslated: Boolean) {
        val correct = str.replace(separator, ".")
        val row = mCalc.sequenceExpression(correct)
        if (row != null) {
            val inline = row.inline() // calculate
            interceptDirect(inline)
            val result = mCalc.calc(inline)
            mView?.showResult(mFormat.format(result))
            if (shouldTranslated) {
                val translated = mCalc.translated()
                interceptTranslated(translated)
            }
        }
    }

    private fun interceptDirect(sequence: List<Member>) {
        val views = ArrayList<CalculatorVisual>(sequence.size)
        moveTo(views, sequence) { it: Member ->
            if (it.operand()) {
                ValueVisualizer(it.view())
            } else {
                OperatorVisualizer(it.view())
            }
        }
        mView?.setRepresentation(views)
    }

    private fun interceptTranslated(translated: Queue<Member>) {
        val now = ArrayList<Visual>(translated.size)
        moveTo(now, translated) { it.view() }
        mView?.setNotation(now)
    }

    private fun <T, V> moveTo(destination: MutableList<T>, origin: Collection<V>, map: (V)-> T) {
        origin.asSequence()
                .map { map(it) }
                .forEach { destination.add(it) }
    }
}
