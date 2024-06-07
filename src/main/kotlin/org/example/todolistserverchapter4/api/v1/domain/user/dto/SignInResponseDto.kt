package org.example.todolistserverchapter4.api.v1.domain.user.dto

import org.example.todolistserverchapter4.api.v1.domain.user.model.User
import org.example.todolistserverchapter4.api.v1.infra.security.jwt.JwtPlugin

data class SignInResponseDto(
    val accessToken: String
) {
    companion object {
        fun from(jwtPlugin: JwtPlugin, user: User): SignInResponseDto {
            return SignInResponseDto(
                accessToken = jwtPlugin.generateAccessToken(
                    subject = user.id.toString(),
                    role = user.role.name
                )
            )
        }
    }
}