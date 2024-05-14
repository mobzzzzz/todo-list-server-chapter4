package org.example.todolistserverchapter3.api.v1.domain.user.service

import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDTO
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDTO
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDTO

interface UserService {

    fun signUp(email: String, password: String, request: SignUpDTO): UserDTO

    fun signIn(email: String, password: String, request: SignInDTO): UserDTO

    fun signOut()

    fun getUserProfile(): UserDTO

    fun updateUserProfile(email: String, password: String): UserDTO
}