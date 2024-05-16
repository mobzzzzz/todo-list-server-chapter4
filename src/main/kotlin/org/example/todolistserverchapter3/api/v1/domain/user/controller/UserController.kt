package org.example.todolistserverchapter3.api.v1.domain.user.controller

import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserUpdateProfileDto
import org.example.todolistserverchapter3.api.v1.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    val userService: UserService
): ApiV1MappingConfig() {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpDto): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request))
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInDto): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(request))
    }

    @PostMapping("/signout")
    fun signOut(): ResponseEntity<Unit> {
        userService.signOut()

        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/users/{user_id}/profile")
    fun getProfile(@PathVariable("user_id") userId: Long): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(userId))
    }

    @PutMapping("/users/{user_id}/profile")
    fun updateProfile(@PathVariable("user_id") userId: Long, @RequestBody request: UserUpdateProfileDto): ResponseEntity<UserDto> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserProfile(userId, request))
    }

    @DeleteMapping("/users/{user_id}/deactivate")
    fun deactivate(@PathVariable("user_id") userId: Long): ResponseEntity<Unit> {
        userService.deactivate(userId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}