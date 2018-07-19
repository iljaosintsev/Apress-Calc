package com.turlir.kotlin

import org.junit.Assert.assertEquals
import org.junit.Test

class DemoTest {

    @Test
    fun demo() {
        val d = Demo("ilja")
        val hi = d.hi()
        assertEquals("hi ilja", hi)

        d.lastName = "Osintcev"
        var fullHi: String = d.fullHi()
        assertEquals("hi ilja Osintcev Mr", fullHi)

        d.lastName = null
        fullHi = d.fullHi()
        assertEquals("hi ilja Mr", fullHi)
    }

}