package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.todo.model.TodoCardStatus
import org.example.todolistserverchapter3.api.v1.domain.todo.model.toDto
import org.example.todolistserverchapter3.api.v1.domain.todo.query.TodoSort
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
    val userRepository: UserRepository,
) : TodoService {
    override fun getTodoList(sort: TodoSort): List<TodoDto> {
        val todos = todoRepository.findAll(
            Sort.by(
                when (sort) {
                    TodoSort.CreatedAtDesc -> Sort.Direction.DESC
                    TodoSort.CreatedAtAsc -> Sort.Direction.ASC
                },
                "created_at"
            )
        )

        return todos.map { it.toDto() }
    }

    override fun getTodo(todoId: Long): TodoDto {
        return todoRepository.findByIdOrNull(todoId)?.toDto() ?: throw ModelNotFoundException("Todo not found", todoId)
    }

    @Transactional
    override fun createTodo(request: TodoCreateDto): TodoDto {
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException(
            "User not found",
            request.userId
        )
        return todoRepository.save(
            Todo(
                title = request.title,
                description = request.description,
                user = user
            )
        ).toDto()
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        val (title, description) = request
        todo.title = title
        todo.description = description

        return todoRepository.save(todo).toDto()
    }

    @Transactional
    override fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        todo.cardStatus = when (request.status) {
            TodoCardStatus.NotStarted.name -> TodoCardStatus.NotStarted
            TodoCardStatus.InProgress.name -> TodoCardStatus.InProgress
            TodoCardStatus.Completed.name -> TodoCardStatus.Completed
            else -> throw IllegalStateException("Invalid card status ${request.status}")
        }

        return todoRepository.save(todo).toDto()
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        todoRepository.delete(todo)
    }
}