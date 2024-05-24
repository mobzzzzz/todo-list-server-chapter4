package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.*
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.CommentRepository
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter3.api.v1.domain.user.service.UserService
import org.example.todolistserverchapter3.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.exception.NoPermissionException
import org.example.todolistserverchapter3.api.v1.util.DtoConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    val todoRepository: TodoRepository,
    val commentRepository: CommentRepository,

    val userService: UserService
) : CommentService {
    override fun getCommentList(todoId: Long, userId: Long, pageable: Pageable): Page<CommentDto> {
        return commentRepository.findByTodoId(todoId, pageable).map { DtoConverter.convertToCommentDto(it) }
    }

    override fun getComment(todoId: Long, commentId: Long, userId: Long): CommentDto {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Todo not found",
            todoId
        )

        return DtoConverter.convertToCommentDto(comment)
    }

    @Transactional
    override fun createComment(todoId: Long, userId: Long, request: CommentCreateWithUserDto): CommentDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val userDto = userService.getUserProfile(userId)

        return DtoConverter.convertToCommentDto(
            commentRepository.save(
                Comment.fromDto(
                    request = request,
                    todo = todo,
                    userDto = userDto,
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
    override fun updateComment(todoId: Long, commentId: Long, userId: Long?, request: CommentUpdateDto): CommentDto {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Comment not found",
            commentId
        )

        if (userId == null) {
            if (!comment.hasPermission(request.password ?: "")) throw NoPermissionException()
        } else {
            if (!comment.hasPermission(userService.getUserProfile(userId))) throw NoPermissionException()
        }

        comment.content = request.content

        return DtoConverter.convertToCommentDto(commentRepository.save(comment))
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long, userId: Long?, request: CommentDeleteWithPasswordDto?) {
        val comment = commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException(
            "Comment not found",
            commentId
        )

        if (userId == null) {
            if (!comment.hasPermission(request?.password ?: "")) throw NoPermissionException()
        } else {
            if (!comment.hasPermission(userService.getUserProfile(userId))) throw NoPermissionException()
        }

        commentRepository.delete(comment)
    }
}