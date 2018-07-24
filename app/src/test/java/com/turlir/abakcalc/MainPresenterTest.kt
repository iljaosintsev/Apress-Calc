package com.turlir.abakcalc

import android.content.Context
import com.nhaarman.mockitokotlin2.*
import com.turlir.calculator.Analyzer
import com.turlir.calculator.Calculator
import com.turlir.calculator.interpreter.PolishInterpreter
import com.turlir.calculator.translator.PolishTranslator
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    private lateinit var mView: MainView

    private lateinit var mPresenter: MainPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val calc = Calculator(Analyzer(), PolishTranslator(), PolishInterpreter())
        mPresenter = MainPresenter(calc)
        assertNotNull(mView)
    }

    @Test
    fun detachTest() {
        mPresenter.attach(mView)
        mPresenter.detach()
        mPresenter.enter("2 + 3 * 4")
        verify(mView, never()).apply {
            showResult(anyString())
            showError(anyString())
            setNotation(any())
            setRepresentation(any())
            resetToEmpty()
        }
    }

    @Test
    fun enterTest() {
        mPresenter.attach(mView)
        mPresenter.enter("2 + 3 * 4")
        verify(mView).setRepresentation(argForWhich { size == 5 } )
        verify(mView).setNotation(argForWhich { size == 5 } )
        verify(mView).showResult(eq("14"))
    }

    @Test
    fun recalculateTest() {
        mPresenter.attach(mView)
        mPresenter.recalculate("2 + 3 * 4")
        verify(mView).setRepresentation(argForWhich { size == 5 } )
        verify(mView, never()).setNotation(any())
        verify(mView).showResult(eq("14"))
    }

    @Test
    fun incompleteExpressionTest() {
        val contextMock = Mockito.mock(Context::class.java)
        whenever(contextMock.getString(ArgumentMatchers.anyInt())).thenReturn("Ошибка")
        whenever(mView.context).thenReturn(contextMock)

        mPresenter.attach(mView)
        mPresenter.enter("2+")
        verify(mView).setRepresentation(argForWhich { size == 2 } )
        verify(mView).showError(same("Ошибка"))
    }

    @Test
    fun bracketErrorTest() {
        mPresenter.attach(mView)
        mPresenter.enter("(5 - 3 * 2")

        verify(mView).setRepresentation(argForWhich { size == 6 } )
        verify(mView).showError(same("Неправильно расставлены скобки"))
    }

}