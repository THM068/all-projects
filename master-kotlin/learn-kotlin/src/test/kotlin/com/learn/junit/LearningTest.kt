package com.learn.junit

import arrow.core.andThen
import org.junit.jupiter.api.Test

class LearningTest {

    @Test
    fun `Kotlin closures`() {
        val myCounter = closureMaker()
        myCounter()
        val x = myCounter.andThen { println("hello you") }
        x()
        x()
        x()
    }
}

fun closureMaker() : () -> Unit {
    var num = 0
    return { println(num++) }
}