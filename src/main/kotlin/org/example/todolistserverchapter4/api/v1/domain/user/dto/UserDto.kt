package org.example.todolistserverchapter4.api.v1.domain.user.dto

import org.example.todolistserverchapter4.api.v1.domain.user.model.User

data class UserDto(
    val id: Long,
    val nickname: String,
    val email: String,
    val role: String,
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                id = user.id!!,
                nickname = user.profile.nickname,
                email = user.email,
                role = user.role.name,
            )
        }
    }
}