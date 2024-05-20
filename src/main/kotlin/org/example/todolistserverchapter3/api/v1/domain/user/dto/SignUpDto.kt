package org.example.todolistserverchapter3.api.v1.domain.user.dto

import jakarta.validation.constraints.Size

data class SignUpDto(
    override val email: String,
    override val password: String,

    @field:Size(min = 2, max = 10, message = "Nickname must be between 2 and 10 characters")
    val nickname: String
) : UserEmailPasswordValidatableDto(email, password)
