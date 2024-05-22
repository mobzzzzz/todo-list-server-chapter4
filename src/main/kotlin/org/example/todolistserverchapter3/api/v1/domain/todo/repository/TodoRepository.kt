package org.example.todolistserverchapter3.api.v1.domain.todo.repository

import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {
    fun findByUserIdIn(userIds: List<Long>, pageable: Pageable = Pageable.unpaged()): Page<Todo>
}