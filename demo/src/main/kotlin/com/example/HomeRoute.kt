    package com.example
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.micronaut.ktor.KtorRoutingBuilder
import javax.inject.Singleton

@Singleton
class HomeRoute(private val nameTransformer: NameTransformer) : KtorRoutingBuilder({
    get("/") {
        call.respondText("Hello world")
    }
})
data class NameRequest(val name: String)