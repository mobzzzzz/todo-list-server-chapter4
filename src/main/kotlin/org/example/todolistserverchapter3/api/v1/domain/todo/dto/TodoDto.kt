package org.example.todolistserverchapter3.api.v1.domain.todo.dto

import java.time.LocalDateTime

data class TodoDto(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String?,
    val status: String,
    val cardStatus: String,
    val createdAt: LocalDateTime
)
