package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDTO

interface TodoService {
    fun getTodos(): List<TodoDTO>
    fun getTodo(todoId: Long): TodoDTO
    fun createTodo(request: TodoCreateDTO): TodoDTO
    fun updateTodo(todoId: Long, request: TodoUpdateDTO): TodoDTO
    fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDTO): TodoDTO
    fun deleteTodo(todoId: Long)
}