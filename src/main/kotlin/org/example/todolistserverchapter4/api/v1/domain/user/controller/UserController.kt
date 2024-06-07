package org.example.todolistserverchapter4.api.v1.domain.user.controller

import jakarta.validation.Valid
import org.example.todolistserverchapter4.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter4.api.v1.domain.user.dto.*
import org.example.todolistserverchapter4.api.v1.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
class UserController(
    val userService: UserService
) : ApiV1MappingConfig() {

    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: SignUpDto,
    ): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request))
    }

    @PostMapping("/signin")
    fun signIn(
        @Valid @RequestBody request: SignInDto,
    ): ResponseEntity<SignInResponseDto> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(request))
    }

    @PostMapping("/signout")
    fun signOut(): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/users/{user_id}/profile")
    fun getProfile(
        @PathVariable("user_id") userId: Long
    ): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(userId))
    }

    @PutMapping("/users/{user_id}/profile")
    fun updateProfile(
        @PathVariable("user_id") userId: Long,
        @Valid @RequestBody request: UserUpdateProfileDto,
    ): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                userService.updateUserProfile(
                    userId = userId,
                    request = request
                )
            )
    }

    @DeleteMapping("/users/{user_id}/deactivate")
    fun deactivate(
        @PathVariable("user_id") userId: Long,
    ): ResponseEntity<Unit> {
        userService.deactivate(userId = userId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}