package org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment

data class CommentCreateWithUserDto(
    override val content: String,
    val userName: String,
    val userId: Long,
) : CommentContentValidatableDto(content)