package org.example.todolistserverchapter3.api.v1.domain.todo.repository

import org.example.todolistserverchapter3.api.v1.domain.todo.model.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByTodoId(todoId: Long, pageable: Pageable = Pageable.unpaged()): Page<Comment>

    fun findByTodoIdAndId(todoId: Long, commentId: Long): Comment?
}
