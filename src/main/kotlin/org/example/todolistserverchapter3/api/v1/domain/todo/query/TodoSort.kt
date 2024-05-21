package org.example.todolistserverchapter3.api.v1.domain.todo.query

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Sort

enum class TodoSort {
    @JsonProperty("created_at_asc")
    CreatedAtAsc,

    @JsonProperty("created_at_desc")
    CreatedAtDesc,
}

fun TodoSort.convertToSort(): Sort {
    return when (this) {
        TodoSort.CreatedAtAsc -> Sort.by("created_at").ascending()
        TodoSort.CreatedAtDesc -> Sort.by("created_at").descending()
    }
}