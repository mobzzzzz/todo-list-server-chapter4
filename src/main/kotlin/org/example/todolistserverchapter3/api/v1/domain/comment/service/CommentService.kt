package org.example.todolistserverchapter3.api.v1.domain.comment.service

import com.sun.tools.javac.comp.Todo
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateDTO
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDTO
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDTO

interface CommentService {
    fun getComments(todoId: Long): List<CommentDTO>

    fun getComment(todoId: Long, commentId: Long): CommentDTO

    fun createComment(todoId: Long, request: CommentCreateDTO): CommentDTO

    fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDTO): CommentDTO

    fun deleteComment(todoId: Long, commentId: Long)
}