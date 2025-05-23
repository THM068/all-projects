package com.kigali.city.com.kigali.city.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import com.kigali.city.com.kigali.city.domains.User
import java.util.Date

object JwtProvider {
    private const val validityInMs = 36_000_00 * 10 // 10 hours
    const val issuer = "ktor-realworld"
    const val audience = "ktor-audience"

    val verifier: JWTVerifier = JWT
            .require(Cipher.algorithm)
            .withIssuer(issuer)
            .build()

    fun decodeJWT(token: String): DecodedJWT = JWT.require(Cipher.algorithm).build().verify(token)

    fun createJWT(user: User): String? =
            JWT.create()
                    .withIssuedAt(Date())
                    .withSubject("Authentication")
                    .withIssuer(issuer)
                    .withAudience(audience)
                    .withClaim("email", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + validityInMs)).sign(Cipher.algorithm)
}