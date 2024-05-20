package org.example.todolistserverchapter3.api.v1.domain.comment.dto

data class CommentCreateWithUserDto(
    override val content: String,

    val userId: Long,
) : CommentContentValidatableDto(content)