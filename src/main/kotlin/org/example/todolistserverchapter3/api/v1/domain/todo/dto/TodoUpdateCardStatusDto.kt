package org.example.todolistserverchapter3.api.v1.domain.todo.dto

import jakarta.validation.constraints.NotBlank

data class TodoUpdateCardStatusDto(

    @field:NotBlank(message = "Status cannot be blank")
    val status: String
)
