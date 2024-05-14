package org.example.todolistserverchapter3.api.v1.domain.user.dto

import org.example.todolistserverchapter3.api.v1.domain.user.model.UserRole
import java.time.LocalDateTime

data class UserDTO(
    val id: Long,
    val nickname: String,
    val email: String,
    val password: String,
    val role: String,
    val registeredAt: LocalDateTime,
    val lastLoginAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)