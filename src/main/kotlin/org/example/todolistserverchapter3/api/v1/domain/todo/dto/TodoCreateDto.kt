package org.example.todolistserverchapter3.api.v1.domain.todo.dto

data class TodoCreateDto(
    val title: String,
    val description: String?
)