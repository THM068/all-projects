package com.example

import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions

@MicronautTest
class UppercaseTransformerSpec(val uppercaseTransformer: UppercaseTransformer): StringSpec({


    "can run UppercaseTransformer"() {
        val result = uppercaseTransformer.transform("hello")

        Assertions.assertEquals("HELLO", result)
    }
})