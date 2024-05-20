package org.example.todolistserverchapter3.api.v1.domain.comment.dto

data class CommentUpdateDto(
    override val content: String
) : CommentContentValidatableDto(content)