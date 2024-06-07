package org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment

data class CommentCreateWithNamePasswordDto(
    override val content: String,

    val name: String,
    val password: String
) : CommentContentValidatableDto(content)
