package org.example.todolistserverchapter4.api.v1.domain.todo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

abstract class TodoTitleDescriptionValidatableDto(
    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(max = 100, message = "Title must be 100 characters or less")
    open val title: String,

    @field:Size(max = 1000, message = "Description must be 1000 characters or less")
    open val description: String?
)