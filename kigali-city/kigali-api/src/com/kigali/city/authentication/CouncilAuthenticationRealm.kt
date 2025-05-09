package com.kigali.city.com.kigali.city.authentication

import com.kigali.city.com.kigali.city.domains.User
import com.kigali.city.com.kigali.city.services.PasswordHashingService
import com.kigali.city.com.kigali.city.services.UserService
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.Permission
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

class CouncilAuthenticationRealm(val userService: UserService, val passwordHashingService: PasswordHashingService) :  AuthorizingRealm() {

    override fun doGetAuthenticationInfo(token: AuthenticationToken?): AuthenticationInfo {
        val usernamePasswordToken = token as UsernamePasswordToken

        val usersOption = userService.findUserByUsername(token.username)
        if(usersOption.isPresent) {
            val user = usersOption.get()

            if(!passwordHashingService.passwordsMatch(user.password, charArrayToString(usernamePasswordToken.password))){
                throw  CredentialsException("Login failed : wrong credentials")
            }
            return SimpleAuthenticationInfo(UserInfo(username = user.username, roles = user.roles, userId = user._id ), usernamePasswordToken.password, "mobaRealm")

        }
        else{
            throw  AuthenticationException("Login name [" + usernamePasswordToken.username + "] not found!")
        }
    }

    override fun doGetAuthorizationInfo(principals: PrincipalCollection?): AuthorizationInfo {
        val roles = mutableSetOf<String>()
        val permissions = mutableSetOf<Permission>()
        val principalsList = principals?.byType(User::class.java)

        if (principalsList != null && principalsList.isEmpty()) {
            throw  AuthorizationException("Empty principals list!");
        }

        if (principalsList != null) {
            for (userPrincipal in principalsList) {
                userPrincipal.username?.let {
                    val userOpt = userService.findUserByUsername(it)
                    if (userOpt.isPresent) {
                        val user = userOpt.get()
                        val userRoles = user.roles

                        for (r in userRoles) {
                            if (r != null) {
                                roles.add(r)
                            } else {
                                throw  AuthenticationException("User not found ...")
                            }
                        }
                    }
                }
            }
        }

        val info = SimpleAuthorizationInfo(roles)
        info.roles = roles //fill in roles
        info.objectPermissions = permissions //add permisions (MUST IMPLEMENT SHIRO PERMISSION INTERFACE)

        return info
    }

    private fun charArrayToString( charArray: CharArray): String {
        val stringBuilder = StringBuilder()
        for(char in charArray) {
            stringBuilder.append(char)
        }
        return stringBuilder.toString()
    }

}
data class UserInfo(val username: String?,val roles: List<String>, val userId: String?)
