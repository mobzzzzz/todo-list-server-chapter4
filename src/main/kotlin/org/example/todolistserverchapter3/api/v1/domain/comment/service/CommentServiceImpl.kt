package org.example.todolistserverchapter3.api.v1.domain.comment.service

import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateDTO
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDTO
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDTO
import org.example.todolistserverchapter3.api.v1.domain.comment.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository
): CommentService {
    override fun getComments(todoId: Long): List<CommentDTO> {
        TODO("Not yet implemented")
    }

    override fun getComment(todoId: Long, commentId: Long): CommentDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createComment(todoId: Long, request: CommentCreateDTO): CommentDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDTO): CommentDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long) {
        TODO("Not yet implemented")
    }
}