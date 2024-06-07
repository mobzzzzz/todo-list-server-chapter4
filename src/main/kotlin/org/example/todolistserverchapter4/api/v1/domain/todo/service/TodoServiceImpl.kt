package org.example.todolistserverchapter4.api.v1.domain.todo.service

import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter4.api.v1.domain.todo.model.status.TodoCardStatus
import org.example.todolistserverchapter4.api.v1.domain.todo.repository.CommentRepository
import org.example.todolistserverchapter4.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter4.api.v1.domain.user.repository.UserRepository
import org.example.todolistserverchapter4.api.v1.exception.ModelNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
    val commentRepository: CommentRepository,
    val userRepository: UserRepository
) : TodoService {

    override fun getTodoList(userIds: List<Long>?, pageable: Pageable): Page<TodoDto> {
        val todos = if (userIds != null) {
            todoRepository.findByUserIdIn(userIds, pageable)
        } else {
            todoRepository.findAll(pageable)
        }

        val users = todos.map { it.userId }.distinct().let { userRepository.findAllById(it) }

        return todos.map { TodoDto.from(todo = it, user = users[it.userId.toInt()]) }
    }

    override fun getTodo(todoId: Long): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val comments = commentRepository.findByTodoId(todoId)
        val user =
            userRepository.findByIdOrNull(todo.userId) ?: throw ModelNotFoundException("User not found", todo.userId)

        return TodoDto.from(todo = todo, user = user, comments = comments.content)
    }

    @Transactional
    override fun createTodo(userId: Long, request: TodoCreateDto): TodoDto {
        val todo = todoRepository.save(
            Todo.fromDto(
                request = request,
                userId = userId
            )
        )

        val user =
            userRepository.findByIdOrNull(todo.userId) ?: throw ModelNotFoundException("User not found", todo.userId)

        return TodoDto.from(todo = todo, user = user)
    }

    @Transactional
    override fun updateTodo(todoId: Long, userId: Long, request: TodoUpdateDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val user =
            userRepository.findByIdOrNull(todo.userId) ?: throw ModelNotFoundException("User not found", todo.userId)

        val (title, description) = request

        todo.title = title
        todo.description = description

        val updatedTodo = todoRepository.save(todo)

        return TodoDto.from(todo = updatedTodo, user = user)
    }

    @Transactional
    override fun updateTodoCardStatus(todoId: Long, userId: Long, request: TodoUpdateCardStatusDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val user =
            userRepository.findByIdOrNull(todo.userId) ?: throw ModelNotFoundException("User not found", todo.userId)

        todo.cardStatus = TodoCardStatus.valueOf(request.status)

        val updatedTodo = todoRepository.save(todo)

        return TodoDto.from(todo = updatedTodo, user = user)
    }

    @Transactional
    override fun deleteTodo(todoId: Long, userId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        val comments = commentRepository.findByTodoId(todoId)

        commentRepository.deleteAll(comments)
        todoRepository.delete(todo)
    }
}