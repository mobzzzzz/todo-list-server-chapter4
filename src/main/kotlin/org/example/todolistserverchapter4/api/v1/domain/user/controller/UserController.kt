package org.example.todolistserverchapter4.api.v1.domain.user.controller

import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.example.todolistserverchapter4.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter4.api.v1.domain.user.dto.SignInDto
import org.example.todolistserverchapter4.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter4.api.v1.domain.user.dto.UserDto
import org.example.todolistserverchapter4.api.v1.domain.user.dto.UserUpdateProfileDto
import org.example.todolistserverchapter4.api.v1.domain.user.service.UserService
import org.example.todolistserverchapter4.api.v1.exception.AlreadyAuthorizedException
import org.example.todolistserverchapter4.api.v1.exception.NotAuthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@SessionAttributes("userId")
@Validated
class UserController(
    val userService: UserService
) : ApiV1MappingConfig() {

    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: SignUpDto,
        @ModelAttribute("userId") currentUserId: Long?,
        model: Model
    ): ResponseEntity<UserDto> {
        if (currentUserId != null) {
            throw AlreadyAuthorizedException()
        }

        val userDto = userService.signUp(request)
        model.addAttribute("userId", userDto.id)

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto)
    }

    @PostMapping("/signin")
    fun signIn(
        @Valid @RequestBody request: SignInDto,
        @ModelAttribute("userId") currentUserId: Long?,
        model: Model
    ): ResponseEntity<UserDto> {
        if (currentUserId != null) {
            throw AlreadyAuthorizedException()
        }

        return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(request))
    }

    @PostMapping("/signout")
    fun signOut(
        @ModelAttribute("userId") currentUserId: Long?,
        session: HttpSession
    ): ResponseEntity<Unit> {
        if (currentUserId == null) {
            throw NotAuthorizedException()
        }

        session.invalidate()

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
        @ModelAttribute("userId") currentUserId: Long?,
        @Valid @RequestBody request: UserUpdateProfileDto,
    ): ResponseEntity<UserDto> {
        if (currentUserId == null) {
            throw NotAuthorizedException()
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                userService.updateUserProfile(
                    userId = userId,
                    currentUserId = currentUserId,
                    request = request
                )
            )
    }

    @DeleteMapping("/users/{user_id}/deactivate")
    fun deactivate(
        @PathVariable("user_id") userId: Long,
        @ModelAttribute("userId") currentUserId: Long?,
    ): ResponseEntity<Unit> {
        if (currentUserId == null) {
            throw NotAuthorizedException()
        }

        userService.deactivate(userId = userId, currentUserId = currentUserId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @ModelAttribute("userId")
    fun getUserIdFromSession(session: HttpSession): Long? {
        return session.getAttribute("userId") as Long?
    }
}