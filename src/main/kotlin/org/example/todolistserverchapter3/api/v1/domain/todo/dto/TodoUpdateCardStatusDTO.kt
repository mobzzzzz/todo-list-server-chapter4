package org.example.todolistserverchapter3.api.v1.domain.todo.dto

import org.example.todolistserverchapter3.api.v1.domain.todo.model.TodoCardStatus

data class TodoUpdateCardStatusDTO(
    val status: String
)
