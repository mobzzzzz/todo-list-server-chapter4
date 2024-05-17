package org.example.todolistserverchapter3.api.v1.domain.user.service

import org.example.todolistserverchapter3.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserUpdateProfileDto
import org.example.todolistserverchapter3.api.v1.domain.user.model.Profile
import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.example.todolistserverchapter3.api.v1.domain.user.model.toDto
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    @Transactional
    override fun signUp(request: SignUpDto): UserDto {
        val user = User(
            email = request.email,
            password = request.password,
            profile = Profile(request.nickname)
        )

        return userRepository.save(user).toDto()
    }

    @Transactional
    override fun signIn(request: SignInDto): UserDto {
        val user = userRepository.findByEmailAndPassword(request.email, request.password)
            ?: throw IllegalStateException("User not found with email")

        return user.toDto()
    }

    @Transactional
    override fun signOut() {
        TODO("아마도 세션을 지우는 로직 구현")
    }

    override fun getUserProfile(userId: Long): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)

        return user.toDto()
    }

    @Transactional
    override fun updateUserProfile(userId: Long, request: UserUpdateProfileDto): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)
        user.profile = Profile(request.nickname)

        return userRepository.save(user).toDto()
    }

    @Transactional
    override fun deactivate(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)

        userRepository.delete(user)
    }
}