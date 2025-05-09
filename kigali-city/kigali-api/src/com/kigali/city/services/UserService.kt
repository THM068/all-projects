package com.kigali.city.com.kigali.city.services

import com.kigali.city.com.kigali.city.authentication.JwtProvider
import com.kigali.city.com.kigali.city.authentication.dologin
import com.kigali.city.com.kigali.city.controller.dto.LoginDto
import com.kigali.city.com.kigali.city.controller.dto.LoginToken
import com.kigali.city.com.kigali.city.controller.dto.RegistrationDTO
import com.kigali.city.com.kigali.city.dao.UserDAO
import com.kigali.city.com.kigali.city.domains.User
import com.kigali.city.com.kigali.city.exceptions.EmailAlreadyExistsException
import com.kigali.city.com.kigali.city.exceptions.UnauthorizedException
import java.util.*

class UserService(val userDao: UserDAO, val passwordHashingService: PasswordHashingService) {

    fun authenticate(loginDto: LoginDto): LoginToken {
        val userOption = findUserByUsername(loginDto.username)
        if(userOption.isPresent) {
            loginDto.let {  dologin(it.username, it.password) }
            val user = userOption.get()
            return LoginToken(token = generateJwtToken(user), username = user.username, roles = user.roles)
        }
        throw UnauthorizedException()
    }


    fun registerUser(registerDto: RegistrationDTO): User {
       return if(doesEmailExist(registerDto)) {
           throw EmailAlreadyExistsException()
       }
       else {
           var user = User.toUser(registerDto)
           user.password = passwordHashingService.hashPassword(user.password)
           return userDao.save(user)
       }
    }

    fun findUserByUsername(username: String?): Optional<User> {
        return userDao.findByUsername(username)
    }

    fun findAll() : List<User> {
        return userDao.findAll()
    }
    private fun doesEmailExist(registerDto: RegistrationDTO): Boolean {
        return userDao.findByUsername(registerDto.email).isPresent
    }

    private fun generateJwtToken(user: User): String? {
        return JwtProvider.createJWT(user)
    }


}