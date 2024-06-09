package org.example.todolistserverchapter4.api.v1.domain.todo.repository

import com.querydsl.core.Tuple
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Comment
import org.example.todolistserverchapter4.api.v1.domain.todo.model.QComment
import org.example.todolistserverchapter4.api.v1.domain.todo.model.QTodo
import org.example.todolistserverchapter4.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter4.api.v1.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class TodoQueryDslRepository : QueryDslSupport() {

    private val todo = QTodo.todo
    private val comment = QComment.comment

    fun findWithComments(todoId: Long): Pair<Todo?, List<Comment>> {
        // querydsl 에서 지원하는 Tuple
        val result: List<Tuple> = queryFactory
            .select(todo, comment)
            .from(todo)
            .leftJoin(comment).on(todo.id.eq(comment.todo.id))
            .where(todo.id.eq(todoId))
            .fetch()

        val todo = result[0].get(todo)
        val comments = result.mapNotNull { it.get(comment) }

        return Pair(todo, comments)
    }
}