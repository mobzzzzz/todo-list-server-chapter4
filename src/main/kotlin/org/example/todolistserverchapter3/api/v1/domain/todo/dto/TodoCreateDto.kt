package org.example.todolistserverchapter3.api.v1.domain.todo.dto

data class TodoCreateDto(
    override val title: String,
    override val description: String?,

    val userId: Long
) : TodoTitleDescriptionValidatableDto(title, description)