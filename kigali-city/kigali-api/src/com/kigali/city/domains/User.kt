package com.kigali.city.com.kigali.city.domains

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.kigali.city.com.kigali.city.controller.dto.RegistrationDTO
import io.ktor.auth.Principal
import java.time.LocalDateTime

@JsonInclude(Include.NON_NULL)
class User: Principal {
    var _id: String? = null
    var name: String? = null
    var lastName: String? = null
    var username: String? = null
    var password: String? = null
    var enabled: Boolean = true
    var accountExpired = false
    var accountLocked = false
    var passwordExpired = false
    var address: String? = null
    var telephone: String? = null
    var city: String? = null
    var country: String? = null
    val roles = listOf<String>("ROLE_RESIDENT")
    var dateCreated: LocalDateTime? = null

    companion object ConvertToUser {
        fun toUser(regDTO: RegistrationDTO) : User {
            return User().apply {
                name = regDTO.name
                lastName = regDTO.lastName
                username = regDTO.email
                password = regDTO.password
                address = regDTO.contact.address
                telephone = regDTO.contact.telephone
                city = regDTO.contact.city
                country = regDTO.contact.country
            }
        }
    }
}
