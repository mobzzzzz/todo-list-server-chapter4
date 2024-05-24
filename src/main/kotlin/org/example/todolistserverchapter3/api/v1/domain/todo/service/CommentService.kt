package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.todo.dto.comment.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {
    /**
     * 댓글 목록을 조회합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param pageable: 페이지, 정렬 정보
     * @return 댓글 목록
     */
    fun getCommentList(todoId: Long, userId: Long, pageable: Pageable): Page<CommentDto>

    /**
     * 단일 댓글을 조회합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param commentId: 댓글 ID
     * @return 댓글 정보
     */
    fun getComment(todoId: Long, commentId: Long, userId: Long): CommentDto

    /**
     * 로그인 된 유저의 댓글을 생성합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param request: 댓글 생성 요청 정보
     * @return 생성된 댓글 정보
     */
    fun createComment(todoId: Long, userId: Long, request: CommentCreateWithUserDto): CommentDto

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
    fun updateComment(todoId: Long, commentId: Long, userId: Long?, request: CommentUpdateDto): CommentDto

    /**
     * 댓글을 삭제합니다.
     *
     * @param todoId: 할 일 카드 ID
     * @param commentId: 댓글 ID
     */
    fun deleteComment(todoId: Long, commentId: Long, userId: Long?, request: CommentDeleteWithPasswordDto?)
}