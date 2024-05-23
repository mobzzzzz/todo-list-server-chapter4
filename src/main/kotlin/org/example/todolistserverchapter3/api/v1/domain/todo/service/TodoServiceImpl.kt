package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.CommentRepository
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.todo.model.status.TodoCardStatus
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter3.api.v1.domain.user.service.UserService
import org.example.todolistserverchapter3.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.util.DtoConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
    val commentRepository: CommentRepository,

    val userService: UserService
) : TodoService {

    override fun getTodoList(userIds: List<Long>?, pageable: Pageable): Page<TodoDto> {
        val todos = if (userIds != null) {
            todoRepository.findByUserIdIn(userIds, pageable)
        } else {
            todoRepository.findAll(pageable)
        }

        val userDtos = todos.map { it.userId }.distinct().let { userService.getUserProfiles(it) }

        return todos.map { DtoConverter.convertToTodoDto(todo = it, userDto = userDtos[it.userId.toInt()]) }
    }

    override fun getTodo(todoId: Long): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val comments = commentRepository.findByTodoId(todoId)
        val userDto = userService.getUserProfile(todo.userId)

        return DtoConverter.convertToTodoDto(todo = todo, userDto = userDto, comments = comments.content)
    }

    @Transactional
    override fun createTodo(request: TodoCreateDto): TodoDto {
        val todo = todoRepository.save(
            Todo.fromDto(
                request = request,
                userId = request.userId
            )
        )

        val userDto = userService.getUserProfile(todo.userId)

        return DtoConverter.convertToTodoDto(todo = todo, userDto = userDto)
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        val (title, description) = request

        todo.title = title
        todo.description = description

        val updatedTodo = todoRepository.save(todo)
        val userDto = userService.getUserProfile(todo.userId)

        return DtoConverter.convertToTodoDto(todo = updatedTodo, userDto = userDto)
    }

    @Transactional
    override fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        todo.cardStatus = TodoCardStatus.valueOf(request.status)

        val updatedTodo = todoRepository.save(todo)
        val userDto = userService.getUserProfile(todo.userId)

        return DtoConverter.convertToTodoDto(todo = updatedTodo, userDto = userDto)
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val comments = commentRepository.findByTodoId(todoId)

        commentRepository.deleteAll(comments)
        todoRepository.delete(todo)
    }

    override fun getCommentList(todoId: Long, pageable: Pageable): Page<CommentDto> {
        return commentRepository.findByTodoId(todoId, pageable).map { DtoConverter.convertToCommentDto(it) }
    }

    override fun getComment(todoId: Long, commentId: Long): CommentDto {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Todo not found",
            todoId
        )

        return DtoConverter.convertToCommentDto(comment)
    }

    @Transactional
    override fun createComment(todoId: Long, request: CommentCreateWithUserDto): CommentDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        return DtoConverter.convertToCommentDto(
            commentRepository.save(
                Comment.fromDto(
                    request = request,
                    todo = todo,
                    userId = request.userId,
                )
            )
        )
    }

    @Transactional
    override fun createComment(todoId: Long, request: CommentCreateWithNamePasswordDto): CommentDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        return DtoConverter.convertToCommentDto(
            commentRepository.save(
                Comment.fromDto(
                    request = request,
                    todo = todo
                )
            )
        )
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDto): CommentDto {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Comment not found",
            commentId
        )

        comment.content = request.content

        return DtoConverter.convertToCommentDto(commentRepository.save(comment))
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long) {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Comment not found",
            commentId
        )

        commentRepository.delete(comment)
    }
}