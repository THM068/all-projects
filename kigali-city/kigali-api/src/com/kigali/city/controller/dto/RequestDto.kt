package com.kigali.city.com.kigali.city.controller.dto

import arrow.core.Either
import arrow.core.Try
import io.ktor.features.MissingRequestParameterException
import io.ktor.http.Parameters
import io.ktor.util.getOrFail

data class RegistrationDTO(var name: String, val lastName: String, val email: String, val password: String, val contact: ContactDTO)
data class ContactDTO(val address: String, val telephone: String, val city: String, val country: String)
data class ErrorMessage(val message: String?)

data class LoginDto(val username: String, val password: String)
data class LoginToken(val token: String?, val username: String?, val roles: List<String>?)


object RequestDto {

    fun toRegistrationDTO(params: Parameters) : Either<ErrorMessage,RegistrationDTO> {
           try {
               val registration = RegistrationDTO(name = params.getOrFail("name"),
                   lastName = params.getOrFail("lastName"),
                   email = params.getOrFail("email"),
                   password = params.getOrFail("password"),
                   contact = toContactDto(params))

               return  Either.right(registration)
           }
           catch(e: MissingRequestParameterException) {
               return Either.left(ErrorMessage(message = e.message))
           }

    }

    private fun toContactDto(params: Parameters): ContactDTO {
        val contact = ContactDTO(
            address = params.getOrFail("address"),
            telephone = params.getOrFail("telephone"),
            city = params.getOrFail("city"),
            country = params.getOrFail("country")
        )
        return contact
    }
}
