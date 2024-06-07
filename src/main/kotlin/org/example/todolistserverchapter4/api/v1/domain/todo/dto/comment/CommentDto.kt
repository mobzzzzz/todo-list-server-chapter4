package org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment

import org.example.todolistserverchapter4.api.v1.domain.todo.model.Comment

data class CommentDto(
    val id: Long,
    val todoId: Long,
    val userId: Long?,
    val content: String,
    val name: String,
    val status: String,
    val createdAt: String
) {
    companion object {
        fun from(comment: Comment): CommentDto {
            return CommentDto(
                id = comment.id!!,
                todoId = comment.todo.id!!,
                userId = comment.user?.id,
                content = comment.content,
                name = comment.userName,
                status = comment.status.name,
                createdAt = comment.createdAt.toString(),
            )
        }
    }
}