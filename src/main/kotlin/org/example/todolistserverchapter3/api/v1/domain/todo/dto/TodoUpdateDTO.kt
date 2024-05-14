package org.example.todolistserverchapter3.api.v1.domain.todo.dto

data class TodoUpdateDTO(
    val title: String,
    val description: String?
)