package org.example.todolistserverchapter3.api.v1.domain.user.service

import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserUpdateProfileDto

interface UserService {

    fun signUp(request: SignUpDto): UserDto

    fun signIn(request: SignInDto): UserDto

    fun signOut()

    fun getUserProfile(userId: Long): UserDto

    fun updateUserProfile(userId: Long, request: UserUpdateProfileDto): UserDto

    fun deactivate(userId: Long)
}
