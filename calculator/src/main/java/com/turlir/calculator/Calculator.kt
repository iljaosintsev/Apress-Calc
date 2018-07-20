package com.turlir.calculator


import com.turlir.calculator.converter.Member
import com.turlir.calculator.interpreter.NotationInterpreter
import com.turlir.calculator.translator.NotationTranslator
import java.math.BigDecimal
import java.util.*

/**
 * Организует взаимодействие между [NotationTranslator] и [NotationInterpreter]
 */
class Calculator
    constructor(private val mTranslator: NotationTranslator, private val mInter: NotationInterpreter) {

    private lateinit var mQueue: Queue<Member>

    /**
     * Вычисляет значение математического выражения
     * @return результата вычисления
     * @throws Exception в случае ошибки трансляции
     */
    @Throws(Exception::class)
    fun calc(sequence: Iterator<Member>): BigDecimal {
        mQueue = mTranslator.translate(sequence)
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
     *
     * @return количество токенов в выражении
     */
    fun size() = mQueue.size

}
