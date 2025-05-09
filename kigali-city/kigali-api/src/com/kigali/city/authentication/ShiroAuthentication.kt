package com.kigali.city.com.kigali.city.authentication

import com.kigali.city.com.kigali.city.exceptions.UnauthorizedException
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken

fun dologin(username: String, password: String) {
    val token = UsernamePasswordToken(username,password);
    token.setRememberMe(true)
    try {
        val currentUser = SecurityUtils.getSubject()
        currentUser.login(token)
    }
    catch(e: Exception){
        throw UnauthorizedException()
    }
}