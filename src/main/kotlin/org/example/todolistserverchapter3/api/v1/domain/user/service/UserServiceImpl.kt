package org.example.todolistserverchapter3.api.v1.domain.user.service

import org.example.todolistserverchapter3.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserUpdateProfileDto
import org.example.todolistserverchapter3.api.v1.domain.user.model.Profile
import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.example.todolistserverchapter3.api.v1.exception.NoPermissionException
import org.example.todolistserverchapter3.api.v1.util.DtoConverter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun signUp(request: SignUpDto): UserDto {
        val encryptedPassword = passwordEncoder.encode(request.password)
        return DtoConverter.convertToUserDto(
            userRepository.save(
                User.fromDto(
                    request.copy(password = encryptedPassword)
                )
            )
        )
    }

    @Transactional
    override fun signIn(request: SignInDto): UserDto {
        val user = userRepository.findByEmailAndPassword(request.email, request.password)
            ?: throw IllegalStateException("User not found with email")

        if (!passwordEncoder.matches(
                request.password,
                user.password
            )
        ) throw IllegalStateException("Password is incorrect")

        return DtoConverter.convertToUserDto(user)
    }

    override fun getUserProfile(userId: Long): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)

        return DtoConverter.convertToUserDto(user)
    }

    override fun getUserProfiles(userIds: List<Long>): List<UserDto> {
        return userRepository.findAllById(userIds).map { DtoConverter.convertToUserDto(it) }
    }

    @Transactional
    override fun updateUserProfile(userId: Long, currentUserId: Long, request: UserUpdateProfileDto): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)
        val currentUser = userRepository.findByIdOrNull(currentUserId) ?: throw ModelNotFoundException(
            "User not found",
            currentUserId
        )

        if (!user.hasPermission(currentUser)) throw NoPermissionException()

        user.updateProfile(Profile(nickname = request.nickname))

        return DtoConverter.convertToUserDto(userRepository.save(user))
    }

    @Transactional
    override fun deactivate(userId: Long, currentUserId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)
        val currentUser = userRepository.findByIdOrNull(currentUserId) ?: throw ModelNotFoundException(
            "User not found",
            currentUserId
        )

        if (!user.hasPermission(currentUser)) throw NoPermissionException()

        userRepository.delete(user)
    }
}