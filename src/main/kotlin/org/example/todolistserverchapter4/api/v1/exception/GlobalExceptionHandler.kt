package org.example.todolistserverchapter4.api.v1.exception

import org.example.todolistserverchapter4.api.v1.exception.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto(e.message, "100"))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto(e.message, "101"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto(e.message, "102"))
    }

    @ExceptionHandler(NotAuthorizedException::class)
    fun handleNotAuthorizedException(e: NotAuthorizedException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDto(e.message, "103"))
    }

    @ExceptionHandler(AlreadyAuthorizedException::class)
    fun handleAlreadyAuthorizedException(e: AlreadyAuthorizedException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDto(e.message, "104"))
    }

    @ExceptionHandler(NoPermissionException::class)
    fun handleNoPermissionException(e: NoPermissionException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorDto(e.message, "105"))
    }
}