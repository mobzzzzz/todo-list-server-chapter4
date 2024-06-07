package org.example.todolistserverchapter4.api.v1.domain.todo.query

import org.example.todolistserverchapter4.api.v1.domain.todo.query.TodoSort.*
import org.springframework.data.domain.Sort

enum class TodoSort {
    CreatedAtAsc,
    CreatedAtDesc,
    TitleAsc,
    TitleDesc,
}

fun TodoSort.convertToSort(): Sort {
    return when (this) {
        CreatedAtAsc -> Sort.by("createdAt").ascending()
        CreatedAtDesc -> Sort.by("createdAt").descending()
        TitleAsc -> Sort.by("title").ascending()
        TitleDesc -> Sort.by("title").descending()
    }
}