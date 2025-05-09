package com.kigali.city.com.kigali.city.services

import com.kigali.city.com.kigali.city.beans.ShiroConfig
import org.apache.shiro.codec.Hex
import org.apache.shiro.crypto.hash.Sha512Hash
import org.apache.shiro.util.ByteSource

class PasswordHashingService(val shiroConfiguration: ShiroConfig) {

    fun  passwordsMatch(dbStoredHashedPassword: String?,clearTextPassword: String): Boolean {
        val hashedPassword = hashAndSaltPassword(clearTextPassword, getSalt())
        return hashedPassword.equals(dbStoredHashedPassword)
    }

    fun hashPassword(clearTextPassword: String?): String {
        val salt = getSalt()
        return hashAndSaltPassword(clearTextPassword, salt)
    }

    private fun hashAndSaltPassword(clearTextPassword: String?, salt: ByteSource): String {
        return Sha512Hash(clearTextPassword, getSalt(), shiroConfiguration.hashIterations).toHex()
    }

    private fun getSalt(): ByteSource {
        return ByteSource.Util.bytes(Hex.decode(shiroConfiguration.hash))
    }
}