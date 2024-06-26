package org.example.todolistserverchapter4.api.v1.domain.todo.repository

import org.example.todolistserverchapter4.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TodoRepository {
    fun findByUserIdIn(userIds: List<Long>, pageable: Pageable = Pageable.unpaged()): Page<Todo>
    fun findWithComments(todoId: Long): Pair<Todo?, List<Comment>>
    fun findAll(pageable: Pageable = Pageable.unpaged()): Page<Todo>
    fun find(todoId: Long): Todo?

    fun save(todo: Todo): Todo
    fun delete(todo: Todo)
}