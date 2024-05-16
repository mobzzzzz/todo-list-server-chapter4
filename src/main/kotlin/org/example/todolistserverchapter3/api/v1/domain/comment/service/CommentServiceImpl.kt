package org.example.todolistserverchapter3.api.v1.domain.comment.service

import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.model.Comment
import org.example.todolistserverchapter3.api.v1.domain.comment.model.toDto
import org.example.todolistserverchapter3.api.v1.domain.comment.repository.CommentRepository
import org.example.todolistserverchapter3.api.v1.domain.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository
) : CommentService {
    override fun getComments(todoId: Long): List<CommentDto> {
        return commentRepository.findAllByTodoId(todoId).map { it.toDto() }
    }

    override fun getComment(todoId: Long, commentId: Long): CommentDto {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Todo not found",
            todoId
        )

        return comment.toDto()
    }

    @Transactional
    override fun createComment(todoId: Long, request: CommentCreateWithUserDto): CommentDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException(
            "User not found",
            request.userId
        )

        return commentRepository.save(
            Comment(
                content = request.content,
                todo = todo,
                user = user,
            )
        ).toDto()
    }

    @Transactional
    override fun createComment(todoId: Long, request: CommentCreateWithNamePasswordDto): CommentDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        return commentRepository.save(
            Comment(
                content = request.content,
                todo = todo,
                name = request.name,
                password = request.password
            )
        ).toDto()
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDto): CommentDto {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Comment not found",
            commentId
        )

        comment.content = request.content

        return commentRepository.save(comment).toDto()
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