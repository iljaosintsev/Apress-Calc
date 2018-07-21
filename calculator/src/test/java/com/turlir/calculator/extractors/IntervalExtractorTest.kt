package com.turlir.calculator.extractors

import com.turlir.calculator.converter.ExpressionPartExtractor
import com.turlir.calculator.converter.MultiOperatorExtractor
import org.junit.Assert.assertFalse
import org.junit.Test

class IntervalExtractorTest : CommonTest() {

    @Test
    fun bracketExtractTest() {
        val extractor = MultiOperatorExtractor(ExpressionPartExtractor("-2*(3+(16-9))"))

        or(extractor, "-")

        and(extractor, "2")

        or(extractor, "*")

        or(extractor, "(")

        and(extractor, "3")

        or(extractor, "+")

        or(extractor, "(")

        and(extractor, "16")

        or(extractor, "-")

        and(extractor, "9")

        or(extractor, ")")

        or(extractor, ")")

        assertFalse(extractor.hasNext())
    }

    @Test
    operator fun unaryMinus() {
        val extractor = MultiOperatorExtractor(ExpressionPartExtractor("-4 + 2"))
        or(extractor, "-")
        and(extractor, "4")
        or(extractor, "+")
        and(extractor, "2")
    }

    @Test
    fun unaryMinusInsideExp() {
        val extractor = MultiOperatorExtractor(ExpressionPartExtractor("4 + 2 * -1"))
        and(extractor, "4")
        or(extractor, "+")
        and(extractor, "2")
        or(extractor, "*")
        or(extractor, "-")
        and(extractor, "1")
    }

    @Test
    fun bracketMultiplyUnaryValue() {
        val extractor = MultiOperatorExtractor(ExpressionPartExtractor("( 2 + 4) * -3"))
        or(extractor, "(")
        and(extractor, "2")
        or(extractor, "+")
        and(extractor, "4")
        or(extractor, ")")
        or(extractor, "*")
        or(extractor, "-")
        and(extractor, "3")
    }

    @Test
    fun floatingNumber() {
        var extractor = ExpressionPartExtractor("2,2")
        and(extractor, "2.2")

        extractor = ExpressionPartExtractor("1 234")
        and(extractor, "1234")
    }

}