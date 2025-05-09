package com.kigali.city.com.kigali.city.exceptions

import com.kigali.city.com.kigali.city.controller.dto.ErrorMessage
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

fun StatusPages.Configuration.exceptionHandlers() {

    exception<UnauthorizedException> { ex ->
        call.respond(HttpStatusCode.Unauthorized, ErrorMessage(ex.message))
    }

    exception<EmailAlreadyExistsException> { ex ->
        call.respond(HttpStatusCode.UnprocessableEntity, ErrorMessage(ex.message))
    }
}