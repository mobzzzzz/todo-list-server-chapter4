package org.example.todolistserverchapter3.api.v1.domain.user.controller

import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDTO
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignInDTO
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDTO
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserUpdateProfileDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController: ApiV1MappingConfig() {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpDTO): ResponseEntity<Unit> {
        // TODO: 사용자 등록 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInDTO): ResponseEntity<Unit> {
        // TODO: 로그인 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PostMapping("/signout")
    fun signOut(): ResponseEntity<Unit> {
        // TODO: 로그아웃 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @GetMapping("/users/{user_id}/profile")
    fun getProfile(@PathVariable("user_id") userId: String): ResponseEntity<UserDTO> {
        // TODO: 사용자 프로필 조회 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(TODO)
    }

    @PutMapping("/users/{user_id}/profile")
    fun updateProfile(@PathVariable("user_id") userId: String, @RequestBody request: UserUpdateProfileDTO): ResponseEntity<UserDTO> {
        // TODO: 사용자 프로필 조회 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(TODO)
    }

    @DeleteMapping("/users/{user_id}/deactivate")
    fun deactivate(@PathVariable("user_id") userId: String): ResponseEntity<Unit> {
        // TODO: 사용자 계정 비활성화 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}