package org.example.todolistserverchapter3.api.v1.util

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto
import org.example.todolistserverchapter3.api.v1.domain.user.model.User

object DtoConverter {
    fun convertToTodoDto(todo: Todo, userDto: UserDto?, comments: List<Comment> = emptyList()): TodoDto {
        return TodoDto(
            id = todo.id!!,
            userId = todo.userId,
            userName = userDto?.nickname ?: "(탈퇴한 유저입니다.)",
            title = todo.title,
            description = todo.description,
            status = todo.status.name,
            cardStatus = todo.cardStatus.name,
            createdAt = todo.createdAt.toString(),
            comments = comments.map { convertToCommentDto(it) }
        )
    }

    fun convertToCommentDto(comment: Comment): CommentDto {
        return CommentDto(
            id = comment.id!!,
            todoId = comment.todo.id!!,
            userId = comment.userId,
            content = comment.content,
            name = comment.userName,
            status = comment.status.name,
            createdAt = comment.createdAt.toString(),
        )
    }

    fun convertToUserDto(user: User): UserDto {
        return UserDto(
            id = user.id!!,
            nickname = user.profile.nickname,
            email = user.email,
            role = user.role.name,
        )
    }
}