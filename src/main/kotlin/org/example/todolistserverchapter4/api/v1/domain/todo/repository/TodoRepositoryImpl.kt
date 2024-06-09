package org.example.todolistserverchapter4.api.v1.domain.todo.repository

import org.example.todolistserverchapter4.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryImpl(
    private val todoJpaRepository: TodoJpaRepository,
    private val todoQueryDslRepository: TodoQueryDslRepository
) : TodoRepository {
    override fun findByUserIdIn(userIds: List<Long>, pageable: Pageable): Page<Todo> {
        return todoJpaRepository.findByUserIdIn(userIds)
    }

    override fun findWithComments(todoId: Long): Pair<Todo?, List<Comment>> {
        return todoQueryDslRepository.findWithComments(todoId)
    }

    override fun findAll(pageable: Pageable): Page<Todo> {
        return todoJpaRepository.findAll(pageable)
    }

    override fun find(todoId: Long): Todo? {
        return todoJpaRepository.findByIdOrNull(todoId)
    }

    override fun save(todo: Todo): Todo {
        return todoJpaRepository.save(todo)
    }

    override fun delete(todo: Todo) {
        todoJpaRepository.delete(todo)
    }
}