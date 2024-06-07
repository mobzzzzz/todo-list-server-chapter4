package org.example.todolistserverchapter4.api.v1.domain.todo.dto

data class TodoUpdateDto(
    override val title: String,
    override val description: String?
) : TodoTitleDescriptionValidatableDto(title, description)