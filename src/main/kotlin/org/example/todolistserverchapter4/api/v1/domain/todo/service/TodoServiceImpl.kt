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
import org.example.todolistserverchapter4.api.v1.exception.NoPermissionException
import org.example.todolistserverchapter4.api.v1.infra.security.SecurityUtils
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

        val users = todos.map { it.user.id }.distinct().let { userRepository.findAllById(it) }

        return todos.map { TodoDto.from(todo = it, user = users[it.user.id!!.toInt()]) }
    }

    override fun getTodo(todoId: Long): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val comments = commentRepository.findByTodoId(todoId)
        val user =
            userRepository.findByIdOrNull(todo.user.id) ?: throw ModelNotFoundException(
                "User not found",
                todo.user.id!!
            )

        return TodoDto.from(todo = todo, user = user, comments = comments.content)
    }

    @Transactional
    override fun createTodo(request: TodoCreateDto): TodoDto {
        val currentUserId = SecurityUtils.getCurrentUserIdOrNull() ?: throw NoPermissionException()
        val user = userRepository.findByIdOrNull(currentUserId) ?: throw ModelNotFoundException(
            "User not found",
            currentUserId
        )

        val todo = todoRepository.save(
            Todo.fromDto(
                request = request,
                user = user
            )
        )
        return TodoDto.from(todo = todo, user = user)
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        if (SecurityUtils.hasPermission(todo.user.id)) throw NoPermissionException()

        val user = userRepository.findByIdOrNull(todo.user.id)
            ?: throw ModelNotFoundException(
                "User not found",
                todo.user.id!!
            )

        val (title, description) = request

        todo.title = title
        todo.description = description

        val updatedTodo = todoRepository.save(todo)

        return TodoDto.from(todo = updatedTodo, user = user)
    }

    @Transactional
    override fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        if (SecurityUtils.hasPermission(todo.user.id)) throw NoPermissionException()

        val user = userRepository.findByIdOrNull(todo.user.id)
            ?: throw ModelNotFoundException(
                "User not found",
                todo.user.id!!
            )

        todo.cardStatus = TodoCardStatus.valueOf(request.status)

        val updatedTodo = todoRepository.save(todo)

        return TodoDto.from(todo = updatedTodo, user = user)
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        if (SecurityUtils.hasPermission(todo.user.id)) throw NoPermissionException()

        val comments = commentRepository.findByTodoId(todoId)

        commentRepository.deleteAll(comments)
        todoRepository.delete(todo)
    }
}