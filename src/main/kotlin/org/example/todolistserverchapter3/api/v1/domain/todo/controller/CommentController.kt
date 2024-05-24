package org.example.todolistserverchapter3.api.v1.domain.todo.controller

import jakarta.validation.Valid
import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.*
import org.example.todolistserverchapter3.api.v1.domain.todo.query.CommentSort
import org.example.todolistserverchapter3.api.v1.domain.todo.query.convertToSort
import org.example.todolistserverchapter3.api.v1.domain.todo.service.CommentService
import org.example.todolistserverchapter3.api.v1.exception.NotAuthorizedException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos/{todo_id}/comments")
@SessionAttributes("user")
@Validated
class CommentController(
    val commentService: CommentService
) : ApiV1MappingConfig() {

    @GetMapping
    fun getCommentList(
        @PathVariable("todo_id") todoId: Long,
        @RequestParam(defaultValue = "createdAtAsc") sort: CommentSort,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @ModelAttribute("userId") userId: Long?
    ): ResponseEntity<Page<CommentDto>> {
        if (userId == null) {
            throw NotAuthorizedException()
        }
        val pageable: Pageable = PageRequest.of(page, size, sort.convertToSort())

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                commentService.getCommentList(
                    todoId = todoId,
                    userId = userId,
                    pageable = pageable
                )
            )
    }

    @GetMapping("/{comment_id}")
    fun getComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        @ModelAttribute("userId") userId: Long?
    ): ResponseEntity<CommentDto> {
        if (userId == null) {
            throw NotAuthorizedException()
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                commentService.getComment(
                    todoId = todoId,
                    userId = userId,
                    commentId = commentId
                )
            )
    }

    @PostMapping
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: CommentCreateWithUserDto,
        @ModelAttribute("userId") userId: Long?
    ): ResponseEntity<CommentDto> {
        if (userId == null) {
            throw NotAuthorizedException()
        }

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                commentService.createComment(
                    todoId = todoId,
                    userId = userId,
                    request = request
                )
            )
    }

    @PostMapping("/anonymous")
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: CommentCreateWithNamePasswordDto
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                commentService.createComment(
                    todoId,
                    request
                )
            )
    }

    @PutMapping("/{comment_id}")
    fun updateComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        @Valid @RequestBody request: CommentUpdateDto,
        @ModelAttribute("userId") userId: Long?
    ): ResponseEntity<CommentDto> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                commentService.updateComment(
                    todoId = todoId,
                    commentId = commentId,
                    userId = userId,
                    request = request
                )
            )
    }

    @DeleteMapping("/{comment_id}")
    fun deleteComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        @ModelAttribute("userId") userId: Long?,
        @RequestBody(required = false) request: CommentDeleteWithPasswordDto? = null,
    ): ResponseEntity<Unit> {
        commentService.deleteComment(
            todoId = todoId,
            commentId = commentId,
            userId = userId,
            request = request
        )

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}