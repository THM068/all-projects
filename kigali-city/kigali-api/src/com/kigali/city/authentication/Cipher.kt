package com.kigali.city.com.kigali.city.authentication

import com.auth0.jwt.algorithms.Algorithm

object Cipher {
    val algorithm = Algorithm.HMAC256("bac11e86-6829-46fa-a2e5-1a1bb3004983")

    fun encrypt(data: String?): ByteArray =
            algorithm.sign(data?.toByteArray())
}
