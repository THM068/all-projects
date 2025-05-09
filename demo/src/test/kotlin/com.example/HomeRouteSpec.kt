package com.example

import io.kotlintest.specs.StringSpec
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class HomeRouteSpec(@Client("/") val client: RxHttpClient): StringSpec({

  "Home route returns text" {
        client.toBlocking().retrieve("/").also {

            println(it)
        }
    }

}) {
}