package org.example.todolistserverchapter4.api.v1.exception.dto

data class ErrorDto(
    val message: String?,
    val errorCode: String
)