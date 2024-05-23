package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.CommentUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TodoService {
    /**
     * 댓글을 제외한 할 일 카드 목록을 불러옵니다.
     *
     * @param userIds: 필터시 필터링 할 유저 ID 목록
     * @param pageable: 페이지, 정렬 정보
     * @return 할 일 카드 목록
     */
    fun getTodoList(userIds: List<Long>? = null, pageable: Pageable): Page<TodoDto>

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

    /**
     * 댓글 목록을 조회합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param pageable: 페이지, 정렬 정보
     * @return 댓글 목록
     */
    fun getCommentList(todoId: Long, pageable: Pageable): Page<CommentDto>

    /**
     * 단일 댓글을 조회합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param commentId: 댓글 ID
     * @return 댓글 정보
     */
    fun getComment(todoId: Long, commentId: Long): CommentDto

    /**
     * 로그인 된 유저의 댓글을 생성합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param request: 댓글 생성 요청 정보
     * @return 생성된 댓글 정보
     */
    fun createComment(todoId: Long, request: CommentCreateWithUserDto): CommentDto

    /**
     * 익명 유저의 댓글을 생성합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param request: 댓글 생성 요청 정보
     * @return 생성된 댓글 정보
     */
    fun createComment(todoId: Long, request: CommentCreateWithNamePasswordDto): CommentDto

    /**
     * 댓글을 수정합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param commentId: 댓글 ID
     * @param request: 댓글 수정 요청 정보
     * @return 수정된 댓글 정보
     */
    fun updateComment(todoId: Long, commentId: Long, request: CommentUpdateDto): CommentDto

    /**
     * 댓글을 삭제합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param commentId: 댓글 ID
     */
    fun deleteComment(todoId: Long, commentId: Long)
}