package org.example.todolistserverchapter3.api.v1.domain.user.service

import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto

interface UserService {

    fun signUp(email: String, password: String, request: SignUpDto): UserDto

    fun signIn(email: String, password: String, request: SignInDto): UserDto

    fun signOut()

    fun getUserProfile(): UserDto

    fun updateUserProfile(email: String, password: String): UserDto
}