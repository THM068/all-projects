package com.kigali.city.com.kigali.city.routes

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application
import io.ktor.http.contentType
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.client.features.json.defaultSerializer
fun Routing.api() {

    val client = HttpClient(Apache){
        install(JsonFeature){
            serializer = JacksonSerializer()
        }
    }

    get("/api/post") {
        val client = HttpClient()
        //"https://jsonplaceholder.typicode.com/posts/1"
        val listOfPosts = client.get<Post>("https://jsonplaceholder.typicode.com/posts/1") {
            accept( Application.Json)
        }
        call.respond(listOfPosts)
    }

    get("/api/all") {
        val list = listOf<Post>(Post(id = 1, userId = 1, title = "hello dolly", body = "It is nice to have you here"))
        call.respond(list)
    }
}

data class Post(val id: Int, val userId: Int,val title: String, val body: String)