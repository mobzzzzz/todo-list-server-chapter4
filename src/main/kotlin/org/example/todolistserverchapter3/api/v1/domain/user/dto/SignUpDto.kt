package org.example.todolistserverchapter3.api.v1.domain.user.dto

data class SignUpDto(
    val email: String,
    val password: String,
    val nickname: String
)