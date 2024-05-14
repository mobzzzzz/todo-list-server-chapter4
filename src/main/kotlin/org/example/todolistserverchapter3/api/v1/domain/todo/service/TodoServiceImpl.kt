package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
): TodoService {
    override fun getTodos(): List<TodoDTO> {
        TODO("Not yet implemented")
    }

    override fun getTodo(todoId: Long): TodoDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createTodo(request: TodoCreateDTO): TodoDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoUpdateDTO): TodoDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDTO): TodoDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        TODO("Not yet implemented")
    }

}