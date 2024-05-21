package org.example.todolistserverchapter3.api.v1.domain.todo.repository

import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {
    fun findAllByUserIdOrderByCreatedAtAsc(userId: Long): List<Todo>
}