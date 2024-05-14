package org.example.todolistserverchapter3.api.v1.domain.user.service

import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDTO
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDTO
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDTO
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    @Transactional
    override fun signUp(email: String, password: String, request: SignUpDTO): UserDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun signIn(email: String, password: String, request: SignInDTO): UserDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun getUserProfile(): UserDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateUserProfile(email: String, password: String): UserDTO {
        TODO("Not yet implemented")
    }
}