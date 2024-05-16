package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.*
import org.example.todolistserverchapter3.api.v1.domain.todo.query.TodoSort

interface TodoService {
    fun getTodoList(sort: TodoSort): List<TodoDto>
    fun getTodo(todoId: Long): TodoDto
    fun createTodo(request: TodoCreateDto): TodoDto
    fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto
    fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto
    fun deleteTodo(todoId: Long)
}