package org.example.todolistserverchapter3.api.v1.domain.todo.controller

import org.example.todolistserverchapter3.api.v1.domain.ApiV1MappingConfig
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDTO
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos")
class TodoController: ApiV1MappingConfig() {

    @GetMapping("/{todo_id}")
    fun getTodo(@PathVariable("todo_id") todoId: String): ResponseEntity<TodoDTO> {
        // TODO: 특정 Todo 항목을 조회하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(TODO)
    }

    @PostMapping
    fun createTodo(@RequestBody request: TodoCreateDTO): ResponseEntity<TodoDTO> {
        // TODO: Todo 항목을 생성하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.CREATED).body(TODO)
    }

    @PutMapping("/{todo_id}")
    fun updateTodo(@PathVariable("todo_id") todoId: String, @RequestBody request: TodoUpdateDTO): ResponseEntity<TodoDTO> {
        // TODO: 특정 Todo 항목을 수정하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(TODO)
    }

    @PutMapping("/{todo_id}/status")
    fun updateTodoCardStatus(@PathVariable("todo_id") todoId: String, @RequestBody request: TodoUpdateCardStatusDTO): ResponseEntity<TodoDTO> {
        // TODO: Todo 항목의 상태를 수정하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.OK).body(TODO)
    }

    @DeleteMapping("/{todo_id}")
    fun deleteTodo(@PathVariable("todo_id") todoId: String): ResponseEntity<Unit> {
        // TODO: 특정 Todo 항목을 삭제하는 로직을 구현하세요.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}