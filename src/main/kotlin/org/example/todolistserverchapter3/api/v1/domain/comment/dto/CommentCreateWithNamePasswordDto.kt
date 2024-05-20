package org.example.todolistserverchapter3.api.v1.domain.comment.dto

data class CommentCreateWithNamePasswordDto(
    override val content: String,

    val name: String,
    val password: String
) : CommentContentValidatableDto(content)
