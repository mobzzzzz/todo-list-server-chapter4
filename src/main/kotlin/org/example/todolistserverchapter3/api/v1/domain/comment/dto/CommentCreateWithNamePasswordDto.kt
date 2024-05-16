package org.example.todolistserverchapter3.api.v1.domain.comment.dto

data class CommentCreateWithNamePasswordDto(
    val content: String,
    val name: String,
    val password: String
)
