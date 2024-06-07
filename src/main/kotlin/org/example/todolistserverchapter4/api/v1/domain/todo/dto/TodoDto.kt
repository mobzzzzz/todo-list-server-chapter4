package org.example.todolistserverchapter4.api.v1.domain.todo.dto

import org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment.CommentDto
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter4.api.v1.domain.user.model.User

data class TodoDto(
    val id: Long,
    val userId: Long,
    val nickname: String,
    val title: String,
    val description: String?,
    val status: String,
    val cardStatus: String,
    val createdAt: String,

    val comments: List<CommentDto>
) {
    companion object {
        fun from(todo: Todo, user: User, comments: List<Comment> = emptyList()): TodoDto {
            return TodoDto(
                id = todo.id!!,
                userId = user.id!!,
                nickname = user.profile.nickname,
                title = todo.title,
                description = todo.description,
                status = todo.status.name,
                cardStatus = todo.cardStatus.name,
                createdAt = todo.createdAt.toString(),
                comments = comments.map { CommentDto.from(it) }
            )
        }
    }
}
