package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.model.Comment
import org.example.todolistserverchapter3.api.v1.domain.comment.query.CommentSort
import org.example.todolistserverchapter3.api.v1.domain.comment.repository.CommentRepository
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.todo.model.TodoCardStatus
import org.example.todolistserverchapter3.api.v1.domain.todo.query.TodoSort
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.example.todolistserverchapter3.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.util.DtoConverter
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
    val userRepository: UserRepository,
    val commentRepository: CommentRepository
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

        return todos.map { DtoConverter.convertToTodoDto(it) }
    }

    override fun getTodo(todoId: Long): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val comments = commentRepository.findAllByTodoIdOrderByCreatedAtAsc(todoId)

        return DtoConverter.convertToTodoDto(todo, comments)
    }

    @Transactional
    override fun createTodo(request: TodoCreateDto): TodoDto {
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException(
            "User not found",
            request.userId
        )

        return DtoConverter.convertToTodoDto(
            todoRepository.save(
                Todo.fromDto(
                    request = request,
                    user = user
                ),
            )
        )
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        val (title, description) = request

        todo.title = title
        todo.description = description

        return DtoConverter.convertToTodoDto(todoRepository.save(todo))
    }

    @Transactional
    override fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        try {
            todo.cardStatus = TodoCardStatus.valueOf(request.status)
        } catch (e: IllegalArgumentException) {
            throw IllegalStateException("Invalid card status ${request.status}")
        }

        return DtoConverter.convertToTodoDto(todoRepository.save(todo))
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val comments = commentRepository.findByTodoId(todoId)

        commentRepository.deleteAll(comments)
        todoRepository.delete(todo)
    }

    override fun getCommentList(todoId: Long, sort: CommentSort): List<CommentDto> {
        return when (sort) {
            CommentSort.CreatedAtAsc -> commentRepository.findAllByTodoIdOrderByCreatedAtAsc(todoId)
            CommentSort.CreatedAtDesc -> commentRepository.findAllByTodoIdOrderByCreatedAtDesc(todoId)
        }.map { DtoConverter.convertToCommentDto(it) }
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
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException(
            "User not found",
            request.userId
        )

        return DtoConverter.convertToCommentDto(
            commentRepository.save(
                Comment.fromDto(
                    request = request,
                    todo = todo,
                    user = user,
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