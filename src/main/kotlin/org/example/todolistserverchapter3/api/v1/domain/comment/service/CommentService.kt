package org.example.todolistserverchapter3.api.v1.domain.comment.service

import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.query.CommentSort

interface CommentService {
    fun getCommentList(todoId: Long, sort: CommentSort): List<CommentDto>

    fun getComment(todoId: Long, commentId: Long): CommentDto

    fun createComment(todoId: Long, request: CommentCreateWithUserDto): CommentDto

    fun createComment(todoId: Long, request: CommentCreateWithNamePasswordDto): CommentDto

    fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDto): CommentDto

    fun deleteComment(todoId: Long, commentId: Long)
}