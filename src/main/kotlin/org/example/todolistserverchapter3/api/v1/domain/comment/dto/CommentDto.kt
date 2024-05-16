package org.example.todolistserverchapter3.api.v1.domain.comment.dto

import java.time.LocalDateTime

data class CommentDto(
    val id: Long,
    val todoId: Long,
    val userId: Long,
    val content: String,
    val name: String,
    val password: String,
    val status: String,
    val createdAt: LocalDateTime
)