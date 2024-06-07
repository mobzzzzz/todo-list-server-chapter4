package org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment

data class CommentUpdateDto(
    override val content: String,

    val password: String? = null
) : CommentContentValidatableDto(content)