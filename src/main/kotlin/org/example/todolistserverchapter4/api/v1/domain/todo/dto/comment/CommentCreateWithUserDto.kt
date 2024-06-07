package org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment

data class CommentCreateWithUserDto(
    override val content: String,
) : CommentContentValidatableDto(content)