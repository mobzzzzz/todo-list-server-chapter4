package org.example.todolistserverchapter4.api.v1.domain.todo.query

import org.springframework.data.domain.Sort

enum class CommentSort {
    CreatedAtAsc,
    CreatedAtDesc,
}

fun CommentSort.convertToSort(): Sort {
    return when (this) {
        CommentSort.CreatedAtAsc -> Sort.by("createdAt").ascending()
        CommentSort.CreatedAtDesc -> Sort.by("createdAt").descending()
    }
}