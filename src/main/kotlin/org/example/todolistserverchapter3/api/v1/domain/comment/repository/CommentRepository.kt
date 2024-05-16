package org.example.todolistserverchapter3.api.v1.domain.comment.repository

import org.example.todolistserverchapter3.api.v1.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByTodoId(todoId: Long): List<Comment>

    fun findByTodoIdAndId(todoId: Long, commentId: Long): Comment?
}
