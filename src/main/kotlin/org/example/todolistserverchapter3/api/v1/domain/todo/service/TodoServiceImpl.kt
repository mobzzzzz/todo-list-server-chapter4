package org.example.todolistserverchapter3.api.v1.domain.todo.service

import org.example.todolistserverchapter3.api.v1.domain.comment.repository.CommentRepository
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateCardStatusDto
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoUpdateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.todo.model.TodoCardStatus
import org.example.todolistserverchapter3.api.v1.domain.todo.query.TodoSort
import org.example.todolistserverchapter3.api.v1.domain.todo.repository.TodoRepository
import org.example.todolistserverchapter3.api.v1.domain.user.repository.UserRepository
import org.example.todolistserverchapter3.api.v1.exception.ModelNotFoundException
import org.example.todolistserverchapter3.api.v1.util.DtoConverter
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    val todoRepository: TodoRepository,
    val userRepository: UserRepository,
    val commentRepository: CommentRepository
) : TodoService {
    /**
     * 댓글을 제외한 할 일 카드 목록을 불러옵니다.
     *
     * @param sort: 생성일 오름/내림차순
     * @return 할 일 카드 목록
     */
    override fun getTodoList(sort: TodoSort): List<TodoDto> {
        val todos = todoRepository.findAll(
            Sort.by(
                when (sort) {
                    TodoSort.CreatedAtDesc -> Sort.Direction.DESC
                    TodoSort.CreatedAtAsc -> Sort.Direction.ASC
                },
                "created_at"
            )
        )

        return todos.map { DtoConverter.convertToTodoDto(it) }
    }

    /**
     * 댓글을 포함하여 할 일 카드 정보를 조회합니다.
     *
     * @param todoId: 조회할 할 일 카드 ID
     * @return 댓글 목록을 포함한 할 일 카드 정보
     */
    override fun getTodo(todoId: Long): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)
        val comments = commentRepository.findAllByTodoIdOrderByCreatedAtAsc(todoId)

        return DtoConverter.convertToTodoDto(todo, comments)
    }

    /**
     * 할 일 카드를 생성해 저장합니다.
     *
     * @param 생성할 할 일 카드 정보
     * @return 생성된 할 일 카드 정보
     */
    @Transactional
    override fun createTodo(request: TodoCreateDto): TodoDto {
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException(
            "User not found",
            request.userId
        )
        return DtoConverter.convertToTodoDto(
            todoRepository.save(
                Todo(
                    title = request.title,
                    description = request.description,
                    user = user
                ),
            )
        )
    }

    /**
     * 할 일 카드 정보를 수정합니다.
     *
     * @param 할 일 카드 ID
     * @param 수정할 할 일 카드 정보
     * @return 수정된 할 일 카드 정보
     */
    @Transactional
    override fun updateTodo(todoId: Long, request: TodoUpdateDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        val (title, description) = request
        todo.title = title
        todo.description = description

        return DtoConverter.convertToTodoDto(todoRepository.save(todo))
    }

    /**
     * 할 일 카드의 진행 상태를 수정합니다.
     *
     * @param 할 일 카드 ID
     * @param 수정할 할 일 카드 진행 상태
     * @return 수정된 할 일 카드 정보
     */
    @Transactional
    override fun updateTodoCardStatus(todoId: Long, request: TodoUpdateCardStatusDto): TodoDto {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        todo.cardStatus = when (request.status) {
            TodoCardStatus.NotStarted.name -> TodoCardStatus.NotStarted
            TodoCardStatus.InProgress.name -> TodoCardStatus.InProgress
            TodoCardStatus.Completed.name -> TodoCardStatus.Completed
            else -> throw IllegalStateException("Invalid card status ${request.status}")
        }

        return DtoConverter.convertToTodoDto(todoRepository.save(todo))
    }

    /**
     * 할 일 카드를 삭제합니다.
     *
     * @param 할 일 카드 ID
     */
    @Transactional
    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo not found", todoId)

        todoRepository.delete(todo)
    }
}