package org.example.todolistserverchapter4.domain.todo.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter4.api.v1.domain.todo.repository.CommentRepository
import org.example.todolistserverchapter4.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter4.api.v1.domain.todo.service.TodoServiceImpl
import org.example.todolistserverchapter4.api.v1.domain.user.model.User
import org.example.todolistserverchapter4.api.v1.domain.user.repository.UserRepository
import org.example.todolistserverchapter4.api.v1.exception.ModelNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull

class TodoServiceTest : BehaviorSpec({
    val todoRepository = mockk<TodoRepository>()
    val commentRepository = mockk<CommentRepository>()
    val userRepository = mockk<UserRepository>()
    val todoService = TodoServiceImpl(todoRepository, commentRepository, userRepository)

    Given("Todo와 User가 존재하는 경우") {
        val todoId = 1L
        val userId = 1L

        // relaxed를 설정하면 모든 메소드의 응답을 설정하지 않아도 예외를 발생시키지 않고 기본적으로 null, 빈 값, 빈 콜렉션 등을 반환함
        val todo = mockk<Todo>(relaxed = true)
        val user = mockk<User>(relaxed = true)
        val comment = mockk<Comment>(relaxed = true)
        val comments = mockk<Page<Comment>>()

        every { todo.id } returns todoId
        every { todo.title } returns "Test Title"
        every { todo.description } returns "Test Description"
        every { todo.user } returns user

        every { user.id } returns userId
        every { user.profile.nickname } returns "Test"

        every { comment.id } returns 1L
        every { comment.content } returns "Test Content"
        every { comments.content } returns listOf(comment)

        every { todoRepository.findByIdOrNull(todoId) } returns todo
        every { userRepository.findByIdOrNull(userId) } returns user
        every { commentRepository.findByTodoId(todoId) } returns comments

        When("getTodo로 Todo 정보를 가져오면") {
            val result = todoService.getTodo(todoId)

            Then("반환된 TodoDto의 정보는 Todo와 User와 Comment의 정보와 같아야 한다") {
                result.id shouldBe todo.id
                result.title shouldBe todo.title
                result.description shouldBe todo.description
                result.userId shouldBe user.id
                result.comments.map { it.id } shouldBe comments.content.map { it.id }
            }
        }
    }

    Given("Todo가 존재하지 않을 때") {
        every { todoRepository.findByIdOrNull(any()) } returns null

        When("특정 Todo를 호출하면") {

            Then("ModelNotFoundException이 발생해야 한다") {
                shouldThrow<ModelNotFoundException> {
                    todoService.getTodo(1L)
                }
            }
        }
    }
})