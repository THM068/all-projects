package com.kigali.city.com.kigali.city.routes

import com.kigali.city.com.kigali.city.controller.UserController
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.routing.get


fun Routing.user(userController: UserController) {

    post("/register") {
        userController.register(this.context)
    }

    post("/login") {
        userController.login(this.context)
    }

    authenticate("jwt",optional = true) {
        get("/hello") {
            call.respond(HttpStatusCode.OK, "Hello world")
        }
    }

    get("/all") {
        userController.all(this.context
        )
    }



}

