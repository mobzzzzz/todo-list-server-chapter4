package org.example.todolistserverchapter3.api.v1.domain.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

abstract class UserEmailPasswordValidatableDto(
    @field:Email(message = "Email must be a valid email address")
    open val email: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    open val password: String
)