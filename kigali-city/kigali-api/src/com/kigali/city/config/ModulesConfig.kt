package com.kigali.city.com.kigali.city.config

import com.kigali.city.com.kigali.city.authentication.CouncilAuthenticationRealm
import com.kigali.city.com.kigali.city.beans.ShiroConfig
import com.kigali.city.com.kigali.city.controller.UserController
import com.kigali.city.com.kigali.city.dao.UserDAO
import com.kigali.city.com.kigali.city.services.PasswordHashingService
import com.kigali.city.com.kigali.city.services.UserService
import io.ktor.application.Application
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.kodein
import java.lang.Integer.parseInt

object ModulesConfig {

    fun init(application: Application) {
        val shiroHash = application.environment.config.property("shiro.hash").getString()
        val shiroHashIterations = parseInt(application.environment.config.property("shiro.hashIterations").getString())

        application.kodein {
            bind<UserService>() with singleton { UserService(UserDAO(), instance()) }
            bind<UserController>() with singleton { UserController(instance()) }
            bind<ShiroConfig>()  with singleton { ShiroConfig(shiroHash, shiroHashIterations) }
            bind<PasswordHashingService>()  with singleton { PasswordHashingService(instance()) }
            bind<CouncilAuthenticationRealm>()  with singleton { CouncilAuthenticationRealm(instance(), instance()) }

        }
    }

}
