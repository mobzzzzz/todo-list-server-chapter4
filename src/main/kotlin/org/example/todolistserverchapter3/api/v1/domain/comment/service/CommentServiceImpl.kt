package org.example.todolistserverchapter3.api.v1.domain.comment.service

import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.model.Comment
import org.example.todolistserverchapter3.api.v1.domain.comment.query.CommentSort
import org.example.todolistserverchapter3.api.v1.domain.comment.repository.CommentRepository
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.example.todolistserverchapter3.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.util.DtoConverter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository
) : CommentService {
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
                Comment(
                    content = request.content,
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
                Comment(
                    content = request.content,
                    todo = todo,
                    name = request.name,
                    password = request.password
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