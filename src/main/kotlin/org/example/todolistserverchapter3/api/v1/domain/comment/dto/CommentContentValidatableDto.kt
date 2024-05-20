package org.example.todolistserverchapter3.api.v1.domain.comment.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

abstract class CommentContentValidatableDto(
    @field:NotBlank
    @field:Size(max = 100, message = "Content must be 100 characters or less")
    open val content: String
)