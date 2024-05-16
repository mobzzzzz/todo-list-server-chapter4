package org.example.todolistserverchapter3.api.v1.domain.comment.service

import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository
): CommentService {
    override fun getComments(todoId: Long): List<CommentDto> {
        TODO("Not yet implemented")
    }

    override fun getComment(todoId: Long, commentId: Long): CommentDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createComment(todoId: Long, request: CommentCreateDto): CommentDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDto): CommentDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long) {
        TODO("Not yet implemented")
    }
}