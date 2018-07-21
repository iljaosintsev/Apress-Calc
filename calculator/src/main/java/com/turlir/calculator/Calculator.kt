package com.turlir.calculator


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
    fun calc(math: String): BigDecimal {
        mDirect = mAnalyzer.slice(math)
        mQueue = mTranslator.translate(mDirect.iterator())
        for (current in mQueue) {
            current.process(mInter)
        }
        return mInter.poolDigit()
    }

    /**
     *
     * @return токены выражения в порядке вычисления
     */
    fun translated() = mQueue


    /**
     * @return токены выражения в прямом порядке (как в исходной строке)
     */
    fun direct() = mDirect

    /**
     *
     * @return количество токенов в выражении
     */
    fun size() = mQueue.size

}
