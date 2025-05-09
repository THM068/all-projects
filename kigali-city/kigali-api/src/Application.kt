package com.kigali.city

import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.core.json.JsonWriteFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kigali.city.com.kigali.city.authentication.CouncilAuthenticationRealm
import com.kigali.city.com.kigali.city.authentication.JwtProvider
import com.kigali.city.com.kigali.city.config.ModulesConfig
import com.kigali.city.com.kigali.city.controller.UserController
import com.kigali.city.com.kigali.city.exceptions.exceptionHandlers
import com.kigali.city.com.kigali.city.routes.api
import com.kigali.city.com.kigali.city.routes.user
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.client.HttpClient
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import org.apache.shiro.SecurityUtils
import org.apache.shiro.mgt.DefaultSecurityManager
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein
import io.ktor.client.engine.HttpClientEngine as HttpClientEngine1

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    ModulesConfig.init(this)
    val userController by kodein().instance<UserController>()
    val councilAuthenticationRealm by kodein().instance<CouncilAuthenticationRealm>()
    iniShiro(councilAuthenticationRealm)



    install(Authentication) {
        jwt("jwt") {
            verifier(JwtProvider.verifier)
            authSchemes("Token")
            validate { credential ->
                if (credential.payload.audience.contains(JwtProvider.audience)) {
                    userController.getUserByEmail(credential.payload.claims["email"]?.asString())
                } else null
            }
        }
    }

    install(StatusPages){
        exceptionHandlers()
    }

    install(Routing) {
        user(userController)
        api()
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModule(JavaTimeModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }
    }
}

fun iniShiro(councilAuthenticationRealm: CouncilAuthenticationRealm) {
    val securityManager = DefaultSecurityManager(councilAuthenticationRealm)
    SecurityUtils.setSecurityManager(securityManager)
}



