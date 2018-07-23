package com.turlir.calculator


import com.turlir.calculator.converter.Expression
import com.turlir.calculator.converter.Member
import com.turlir.calculator.interpreter.NotationInterpreter
import com.turlir.calculator.translator.NotationTranslator
import java.math.BigDecimal
import java.util.*

/**
 * Организует взаимодействие между [NotationTranslator] и [NotationInterpreter]
 */
class Calculator (
            private val mAnalyzer: Analyzer,
            private val mTranslator: NotationTranslator,
            private val mInter: NotationInterpreter
    ) {

    private lateinit var mDirect: List<Member>
    private lateinit var mQueue: Queue<Member>

    /**
     * Вычисляет значение математического выражения
     * @return результата вычисления
     * @throws Exception в случае ошибки трансляции
     */
    @Throws(Exception::class)
    fun calc(direct: List<Member>): BigDecimal {
        mQueue = mTranslator.translate(direct.iterator())
        for (current in mQueue) {
            current.process(mInter)
        }
        return mInter.poolDigit()
    }

    @Throws(Exception::class)
    fun calcExpression(direct: Expression): BigDecimal {
        return calc(direct.inline())
    }

    fun direct(math: String): List<Member> {
        mDirect = mAnalyzer.slice(math)
        return mDirect
    }

    fun directExpression(math: String): Expression? {
        return mAnalyzer.expression(math)
    }

    /**
     *
     * @return токены выражения в порядке вычисления
     */
    fun translated() = mQueue

}
