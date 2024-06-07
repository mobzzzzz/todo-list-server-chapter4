package org.example.todolistserverchapter4.api.v1.domain.user.service

import org.example.todolistserverchapter4.api.v1.domain.user.dto.*
import org.example.todolistserverchapter4.api.v1.domain.user.model.Profile
import org.example.todolistserverchapter4.api.v1.domain.user.model.User
import org.example.todolistserverchapter4.api.v1.domain.user.repository.UserRepository
import org.example.todolistserverchapter4.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter4.api.v1.exception.NoPermissionException
import org.example.todolistserverchapter4.api.v1.infra.security.SecurityUtils
import org.example.todolistserverchapter4.api.v1.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {

    @Transactional
    override fun signUp(request: SignUpDto): UserDto {
        if (userRepository.existsByEmail(request.email)) throw IllegalStateException("Email already in use")

        val encryptedPassword = passwordEncoder.encode(request.password)

        return UserDto.from(
            userRepository.save(
                User.fromDto(
                    request.copy(password = encryptedPassword)
                )
            )
        )
    }

    @Transactional
    override fun signIn(request: SignInDto): SignInResponseDto {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", 0)

        if (!passwordEncoder.matches(request.password, user.password)) throw NoPermissionException()

        return SignInResponseDto.from(jwtPlugin, user)
    }

    override fun getUserProfile(userId: Long): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)

        if (!SecurityUtils.hasPermission(user.id!!)) throw NoPermissionException()

        return UserDto.from(user)
    }

    @Transactional
    override fun updateUserProfile(userId: Long, request: UserUpdateProfileDto): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)

        if (!SecurityUtils.hasPermission(user.id!!)) throw NoPermissionException()

        user.updateProfile(Profile(nickname = request.nickname))

        return UserDto.from(userRepository.save(user))
    }

    @Transactional
    override fun deactivate(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User not found", userId)

        if (!SecurityUtils.hasPermission(user.id!!)) throw NoPermissionException()

        userRepository.delete(user)
    }
}