package org.example.todolistserverchapter3.api.v1.domain.todo.dto

data class TodoDto(
    val id: Long,
    val userId: Long,
    val userName: String,
    val title: String,
    val description: String?,
    val status: String,
    val cardStatus: String,
    val createdAt: String
)
