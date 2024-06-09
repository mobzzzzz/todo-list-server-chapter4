package org.example.todolistserverchapter4.domain.comment.controller

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.example.todolistserverchapter4.api.v1.domain.todo.controller.CommentController
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment.CommentDto
import org.example.todolistserverchapter4.api.v1.domain.todo.dto.comment.CommentUpdateDto
import org.example.todolistserverchapter4.api.v1.domain.todo.service.CommentService
import org.example.todolistserverchapter4.api.v1.exception.ModelNotFoundException
import org.springframework.http.HttpStatus

class CommentControllerTest : BehaviorSpec({
    val commentService = mockk<CommentService>()
    val commentController = CommentController(commentService)

    Given("CommentUpdateDto의 password가 null인 경우 (익명 유저)") {
        val todoId = 1L
        val commentId = 1L
        val commentUpdateDto = CommentUpdateDto(content = "Updated content", password = null)
        val commentDto = mockk<CommentDto>(relaxed = true)

        every { commentDto.userId } returns null
        every { commentService.updateComment(todoId, commentId, commentUpdateDto) } returns commentDto

        When("PUT 요청이 들어와 updateComment 메서드가 실행되면") {
            val result = commentController.updateComment(todoId, commentId, commentUpdateDto)

            Then("Response의 상태 코드는 OK이어야 하고 userId는 null이어야 한다") {
                result.statusCode shouldBe HttpStatus.OK
                result.body?.userId shouldBe null
            }
        }
    }

    Given("CommentUpdateDto의 password가 null이 아닌 경우 (로그인 유저)") {
        val todoId = 1L
        val commentId = 1L
        val commentUpdateDto = CommentUpdateDto(content = "Updated content", password = "password")
        val commentDto = mockk<CommentDto>(relaxed = true)

        every { commentDto.userId } returns 1L
        every { commentService.updateComment(todoId, commentId, commentUpdateDto) } returns commentDto

        When("PUT 요청이 들어와 updateComment 메서드가 실행되면") {
            val result = commentController.updateComment(todoId, commentId, commentUpdateDto)

            Then("Response의 상태 코드는 OK이어야 하고 userId는 null이 아니어야 한다") {
                result.statusCode shouldBe HttpStatus.OK
                result.body?.userId shouldNotBe null
            }
        }
    }

    Given("Comment service가 해당 댓글을 찾지 못한 경우") {
        val todoId = 1L
        val commentId = 1L
        val commentUpdateDto = CommentUpdateDto(content = "Updated content", password = "password")

        every {
            commentService.updateComment(
                todoId,
                commentId,
                commentUpdateDto
            )
        } throws ModelNotFoundException("Comment not found", commentId)

        When("PUT 요청이 들어와 updateComment 메서드가 실행되면") {
            Then("ModelNotFoundException이 발생해야 한다") {
                shouldThrow<ModelNotFoundException> {
                    commentController.updateComment(todoId, commentId, commentUpdateDto)
                }
            }
        }
    }
})