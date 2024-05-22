package org.example.todolistserverchapter3.api.v1.domain.comment.controller

import jakarta.validation.Valid
import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.query.CommentSort
import org.example.todolistserverchapter3.api.v1.domain.comment.query.convertToSort
import org.example.todolistserverchapter3.api.v1.domain.todo.service.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos/{todo_id}/comments")
@Validated
class CommentController(
    val todoService: TodoService
) : ApiV1MappingConfig() {

    @GetMapping
    fun getCommentList(
        @PathVariable("todo_id") todoId: Long,
        @RequestParam(defaultValue = "created_at_asc") sort: CommentSort,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Page<CommentDto>> {
        val pageable: Pageable = PageRequest.of(page, size, sort.convertToSort())

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getCommentList(todoId, pageable))
    }

    @GetMapping("/{comment_id}")
    fun getComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getComment(todoId, commentId))
    }

    @PostMapping
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: CommentCreateWithUserDto
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createComment(todoId, request))
    }

    @PostMapping("/anonymous")
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: CommentCreateWithNamePasswordDto
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createComment(todoId, request))
    }

    @PutMapping("/{comment_id}")
    fun updateComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        @Valid @RequestBody request: CommentUpdateDto
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateComment(todoId, commentId, request))
    }

    @DeleteMapping("/{comment_id}")
    fun deleteComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long
    ): ResponseEntity<Unit> {
        todoService.deleteComment(todoId, commentId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}