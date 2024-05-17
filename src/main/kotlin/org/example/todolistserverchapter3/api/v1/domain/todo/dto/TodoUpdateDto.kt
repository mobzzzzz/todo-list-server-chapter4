package org.example.todolistserverchapter3.api.v1.domain.todo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TodoUpdateDto(

    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(max = 100, message = "Title must be 1000 characters or less")
    val title: String,

    @field:Size(max = 1000, message = "Description must be 1000 characters or less")
    val description: String?
)