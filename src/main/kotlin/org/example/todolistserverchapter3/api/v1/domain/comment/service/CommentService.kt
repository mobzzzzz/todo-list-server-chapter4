package org.example.todolistserverchapter3.api.v1.domain.comment.service

import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto

interface CommentService {
    fun getComments(todoId: Long): List<CommentDto>

    fun getComment(todoId: Long, commentId: Long): CommentDto

    fun createComment(todoId: Long, request: CommentCreateDto): CommentDto

    fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDto): CommentDto

    fun deleteComment(todoId: Long, commentId: Long)
}