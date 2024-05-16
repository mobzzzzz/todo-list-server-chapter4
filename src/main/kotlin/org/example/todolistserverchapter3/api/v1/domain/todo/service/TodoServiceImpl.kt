package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
): TodoService {
    override fun getTodos(): List<TodoDto> {
        return todoRepository.findAll().map { it.toDTO() }
    }

    override fun getTodo(todoId: Long): TodoDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createTodo(request: TodoCreateDto): TodoDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        TODO("Not yet implemented")
    }

}