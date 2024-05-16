package org.example.todolistserverchapter3.api.v1.domain.comment.controller

import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos/{todo_id}/comments")
class CommentController(
    val commentService: CommentService
) : ApiV1MappingConfig() {

    @GetMapping
    fun getComments(@PathVariable("todo_id") todoId: Long): ResponseEntity<List<CommentDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(todoId))
    }

    @GetMapping("/{comment_id}")
    fun getComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(todoId, commentId))
    }

    @PostMapping
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @RequestBody request: CommentCreateWithUserDto
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(todoId, request))
    }

    @PostMapping("/anonymous")
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @RequestBody request: CommentCreateWithNamePasswordDto
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(todoId, request))
    }

    @PutMapping("/{comment_id}")
    fun updateComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        @RequestBody request: CommentUpdateDto
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(todoId, commentId, request))
    }

    @DeleteMapping("/{comment_id}")
    fun deleteComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(todoId, commentId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}