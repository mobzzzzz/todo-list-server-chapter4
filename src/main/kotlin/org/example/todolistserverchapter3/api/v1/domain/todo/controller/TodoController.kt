package org.example.todolistserverchapter3.api.v1.domain.todo.controller

import jakarta.validation.Valid
import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.query.TodoSort
import org.example.todolistserverchapter3.api.v1.domain.todo.query.convertToSort
import org.example.todolistserverchapter3.api.v1.domain.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos")
@Validated
class TodoController(
    private val todoService: TodoService
) : ApiV1MappingConfig() {

    @GetMapping
    fun getTodoList(
        @RequestParam(defaultValue = "created_at_asc") sort: TodoSort,
        @RequestParam(required = false) userId: Long? = null,
    ): ResponseEntity<List<TodoDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoList(sort.convertToSort(), userId))
    }

    @GetMapping("/{todo_id}")
    fun getTodo(@PathVariable("todo_id") todoId: Long): ResponseEntity<TodoDto> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodo(todoId))
    }

    @PostMapping
    fun createTodo(@Valid @RequestBody request: TodoCreateDto): ResponseEntity<TodoDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(request))
    }

    @PutMapping("/{todo_id}")
    fun updateTodo(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: TodoUpdateDto
    ): ResponseEntity<TodoDto> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodo(todoId, request))
    }

    @PutMapping("/{todo_id}/status")
    fun updateTodoCardStatus(
        @PathVariable("todo_id") todoId: Long,
        @Valid @RequestBody request: TodoUpdateCardStatusDto
    ): ResponseEntity<TodoDto> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodoCardStatus(todoId, request))
    }

    @DeleteMapping("/{todo_id}")
    fun deleteTodo(@PathVariable("todo_id") todoId: Long): ResponseEntity<Unit> {
        todoService.deleteTodo(todoId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}