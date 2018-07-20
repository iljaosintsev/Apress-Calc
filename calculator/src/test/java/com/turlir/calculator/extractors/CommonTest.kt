package com.turlir.calculator.extractors

import com.turlir.calculator.converter.Member
import com.turlir.calculator.extractors.Interval
import org.junit.Assert

abstract class CommonTest {

    internal fun and(it: Iterator<Interval>, value: String) {
        Assert.assertTrue(it.hasNext())
        Assert.assertEquals(it.next(), and(value))
    }

    internal fun or(it: Iterator<Interval>, value: String) {
        Assert.assertTrue(it.hasNext())
        Assert.assertEquals(it.next(), or(value))
    }

    //

    protected fun and(it: Iterator<Member>, value: Member) {
        Assert.assertTrue(it.hasNext())
        Assert.assertEquals(it.next(), value)
    }

    protected fun or(it: Iterator<Member>, value: Member) {
        Assert.assertTrue(it.hasNext())
        Assert.assertTrue(it.next() === value)
    }

    //

    protected fun or(value: String): Interval {
        return Interval(false, value)
    }

    protected fun and(value: String): Interval {
        return Interval(true, value)
    }

}
