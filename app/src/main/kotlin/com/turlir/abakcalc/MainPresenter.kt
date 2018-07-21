package com.turlir.abakcalc

import com.turlir.calculator.Calculator
import com.turlir.calculator.converter.Member
import com.turlir.calculator.converter.Visual
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

internal class MainPresenter(private val mCalc: Calculator) {

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
        if (s == null || s.isEmpty()) {
            mView!!.resetToEmpty()
            return
        }

        try {
            val result = calculate(s)
            showRepresentation(result)
            showNotation()

        } catch (e: EmptyStackException) {
            mView!!.showError(mView!!.context().getString(R.string.error))
            mView!!.setNotation(emptyList())

        } catch (e: NoSuchElementException) {
            mView!!.showError(mView!!.context().getString(R.string.error))
            mView!!.setNotation(emptyList())

        } catch (e: Exception) {
            mView!!.showError(e.message!!)
            mView!!.setNotation(emptyList())
        }
    }

    fun recalculate(s: String?) {
        if (s == null || s.isEmpty()) {
            mView!!.resetToEmpty()
            return
        }
        try {
            val result = calculate(s)
            showRepresentation(result)
        } catch (e: Exception) {
            showRepresentation()
        }
    }

    private fun showRepresentation(result: BigDecimal) {
        mView!!.showResult(mFormat.format(result))
        showRepresentation()
    }

    private fun showRepresentation() {
        val copy = mCalc.direct()
        val visual = interceptDirect(copy)
        mView!!.setRepresentation(visual)
    }

    private fun showNotation() {
        val row = mCalc.translated()
        val now = ArrayList<Visual>(mCalc.size())
        for (item in row) now.add(item.view())
        mView!!.setNotation(now)
    }

    @Throws(Exception::class)
    private fun calculate(str: String): BigDecimal {
        val copy = str.replace(separator, ".")
        return mCalc.calc(copy)
    }

    private fun interceptDirect(sequence: Collection<Member>): List<CalculatorVisual> {
        val views = ArrayList<CalculatorVisual>(sequence.size)
        moveTo(views, sequence) { it: Member ->
            if (it.operand()) {
                ValueVisualizer(it.view())
            } else {
                OperatorVisualizer(it.view())
            }
        }
        return views
    }

    private fun <T, V> moveTo(destination: MutableCollection<T>, origin: Collection<V>, map: (V)-> T) =
        origin.map { map(it) }
                .forEach { destination.add(it) }
}
