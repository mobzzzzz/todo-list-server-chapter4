package org.example.todolistserverchapter4.api.v1.domain.user.dto

import jakarta.validation.constraints.Size

data class UserUpdateProfileDto(
    @field:Size(min = 2, max = 10, message = "Nickname must be between 2 and 10 characters")
    val nickname: String
)
