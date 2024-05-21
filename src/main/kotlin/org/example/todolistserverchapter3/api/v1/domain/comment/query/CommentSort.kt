package org.example.todolistserverchapter3.api.v1.domain.comment.query

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Sort

enum class CommentSort {
    @JsonProperty("created_at_asc")
    CreatedAtAsc,

    @JsonProperty("created_at_desc")
    CreatedAtDesc,
}

fun CommentSort.convertToSort(): Sort {
    return when (this) {
        CommentSort.CreatedAtAsc -> Sort.by("created_at").ascending()
        CommentSort.CreatedAtDesc -> Sort.by("created_at").descending()
    }
}