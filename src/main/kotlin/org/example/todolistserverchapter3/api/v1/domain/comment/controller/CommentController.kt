package org.example.todolistserverchapter3.api.v1.domain.comment.controller

import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentUpdateDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos/{todo_id}/comments")
class CommentController: ApiV1MappingConfig() {

    @GetMapping
    fun getComments(@PathVariable("todo_id") todoId: String): ResponseEntity<List<CommentDto>> {
        // TODO: Comment 항목들을 조회하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT)
    }

    @GetMapping("/{comment_id}")
    fun getComment(@PathVariable("todo_id") todoId: String, @PathVariable("comment_id") commentId: String): ResponseEntity<CommentDto> {
        // TODO: 특정 Comment 항목을 조회하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT)
    }

    @PostMapping
    fun createComment(@PathVariable("todo_id") todoId: String, @RequestBody request: CommentCreateDto): ResponseEntity<CommentDto> {
        // TODO: Comment 항목을 생성하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.CREATED).body(COMMENT)
    }

    @PutMapping("/{comment_id}")
    fun updateComment(@PathVariable("todo_id") todoId: String, @PathVariable("comment_id") commentId: String, @RequestBody request: CommentUpdateDto): ResponseEntity<CommentDto> {
        // TODO: 특정 Comment 항목을 수정하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT)
    }

    @DeleteMapping("/{comment_id}")
    fun deleteComment(@PathVariable("todo_id") todoId: String, @PathVariable("comment_id") commentId: String): ResponseEntity<Unit> {
        // TODO: 특정 Comment 항목을 삭제하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}