package org.example.todolistserverchapter4.api.v1.domain.todo.dto

import org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment.CommentDto

data class TodoDto(
    val id: Long,
    val userId: Long,
    val userName: String,
    val title: String,
    val description: String?,
    val status: String,
    val cardStatus: String,
    val createdAt: String,

    val comments: List<CommentDto>
)
