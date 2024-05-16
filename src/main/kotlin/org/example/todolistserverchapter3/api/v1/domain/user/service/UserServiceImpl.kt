package org.example.todolistserverchapter3.api.v1.domain.user.service

import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    @Transactional
    override fun signUp(email: String, password: String, request: SignUpDto): UserDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun signIn(email: String, password: String, request: SignInDto): UserDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun getUserProfile(): UserDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateUserProfile(email: String, password: String): UserDto {
        TODO("Not yet implemented")
    }
}