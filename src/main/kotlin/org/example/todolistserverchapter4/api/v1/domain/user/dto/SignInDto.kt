package org.example.todolistserverchapter4.api.v1.domain.user.dto

data class SignInDto(
    override val email: String,
    override val password: String
) : UserEmailPasswordValidatableDto(email, password)
