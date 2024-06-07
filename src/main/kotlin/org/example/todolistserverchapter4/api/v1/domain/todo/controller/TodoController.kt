package org.example.todolistserverchapter4.api.v1.domain.todo.controller

import jakarta.validation.Valid
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter4.api.v1.domain.todo.model.status.TodoCardStatus
import org.example.todolistserverchapter4.api.v1.domain.todo.query.TodoSort
import org.example.todolistserverchapter4.api.v1.domain.todo.query.convertToSort
import org.example.todolistserverchapter4.api.v1.domain.todo.service.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/todos")
@Validated
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping
    fun getTodoList(
        @RequestParam(defaultValue = "createdAtAsc") sort: TodoSort,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) userIds: List<Long>? = null,
    ): ResponseEntity<Page<TodoDto>> {
        val pageable: Pageable = PageRequest.of(page, size, sort.convertToSort())

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                todoService.getTodoList(
                    userIds = userIds,
                    pageable = pageable
                )
            )
    }

    @GetMapping("/{todo_id}")
    fun getTodo(
        @PathVariable("todo_id") todoId: Long
    ): ResponseEntity<TodoDto> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                todoService.getTodo(todoId = todoId)
            )
    }

    @PostMapping
    fun createTodo(
        @Valid @RequestBody request: TodoCreateDto,
    ): ResponseEntity<TodoDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                todoService.createTodo(
                    request = request
                )
            )
    }

    @PutMapping("/{todo_id}")
    fun updateTodo(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: TodoUpdateDto,
    ): ResponseEntity<TodoDto> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                todoService.updateTodo(
                    todoId = todoId,
                    request = request
                )
            )
    }

    @PutMapping("/{todo_id}/status")
    fun updateTodoCardStatus(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: TodoUpdateCardStatusDto,
    ): ResponseEntity<TodoDto> {
        if (!TodoCardStatus.entries.map { it.name }.contains(request.status)) {
            throw IllegalArgumentException("Invalid card status ${request.status}")
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                todoService.updateTodoCardStatus(
                    todoId = todoId,
                    request = request
                )
            )
    }

    @DeleteMapping("/{todo_id}")
    fun deleteTodo(
        @PathVariable("todo_id") todoId: Long,
    ): ResponseEntity<Unit> {
        todoService.deleteTodo(todoId = todoId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}