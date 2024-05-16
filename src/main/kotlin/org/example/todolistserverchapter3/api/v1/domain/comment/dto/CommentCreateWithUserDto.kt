package org.example.todolistserverchapter3.api.v1.domain.comment.dto

data class CommentCreateWithUserDto(
    val content: String,
    val userId: Long,
)