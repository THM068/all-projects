package com.kigali.city.com.kigali.city.controller

import arrow.core.Either
import arrow.syntax.function.pipe10
import com.kigali.city.com.kigali.city.controller.dto.ErrorMessage
import com.kigali.city.com.kigali.city.controller.dto.LoginDto
import com.kigali.city.com.kigali.city.controller.dto.RequestDto
import com.kigali.city.com.kigali.city.dao.PageRequest
import com.kigali.city.com.kigali.city.domains.User
import com.kigali.city.com.kigali.city.exceptions.UnauthorizedException
import com.kigali.city.com.kigali.city.services.UserService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.util.getOrFail
import java.lang.IllegalArgumentException

class UserController(val userService: UserService) {

    suspend fun register(call: ApplicationCall) {
        val registrationEither = RequestDto.toRegistrationDTO(call.receiveParameters())
        when(registrationEither){
            is Either.Left  -> call.respond(HttpStatusCode.UnprocessableEntity, registrationEither.a)
            is Either.Right -> {
                userService.registerUser(registrationEither.b)
                call.respond(HttpStatusCode.Created)
                }
            }
        }

    suspend fun login(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val loginDto = LoginDto(parameters.getOrFail("username"), parameters.getOrFail("password"))
        loginDto.apply {
            userService.authenticate(this).apply {
                call.respond(this)
            }
        }
    }

    suspend fun all(call: ApplicationCall) {
        call.respond(userService.findAll())
    }

    fun getUserByEmail(email: String?): User {
        return email.let {
            require(!it.isNullOrBlank()) { "User not logged or with invalid email." }
            val userOpt = userService.findUserByUsername(it)
            if(userOpt.isPresent) {
                return userOpt.get()
            }
            throw IllegalArgumentException()
        }
    }


}