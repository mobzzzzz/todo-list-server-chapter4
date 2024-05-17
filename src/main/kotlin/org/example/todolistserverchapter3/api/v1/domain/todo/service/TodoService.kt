package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.*
import org.example.todolistserverchapter3.api.v1.domain.todo.query.TodoSort

interface TodoService {
    /**
     * 댓글을 제외한 할 일 카드 목록을 불러옵니다.
     *
     * @param sort: 생성일 오름/내림차순
     * @return 할 일 카드 목록
     */
    fun getTodoList(sort: TodoSort): List<TodoDto>

    /**
     * 댓글을 포함하여 할 일 카드 정보를 조회합니다.
     *
     * @param todoId: 조회할 할 일 카드 ID
     * @return 댓글 목록을 포함한 할 일 카드 정보
     */
    fun getTodo(todoId: Long): TodoDto

    /**
     * 할 일 카드를 생성해 저장합니다.
     *
     * @param 생성할 할 일 카드 정보
     * @return 생성된 할 일 카드 정보
     */
    fun createTodo(request: TodoCreateDto): TodoDto

    /**
     * 할 일 카드 정보를 수정합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param request: 수정할 할 일 카드 정보
     * @return 수정된 할 일 카드 정보
     */
    fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto

    /**
     * 할 일 카드의 진행 상태를 수정합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param request: 수정할 할 일 카드 진행 상태
     * @return 수정된 할 일 카드 정보
     */
    fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto

    /**
     * 할 일 카드를 삭제합니다.
     *
     * @param 할 일 카드 ID
     */
    fun deleteTodo(todoId: Long)
}