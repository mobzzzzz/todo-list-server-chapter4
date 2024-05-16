package org.example.todolistserverchapter3.api.v1.domain.comment.query

import com.fasterxml.jackson.annotation.JsonProperty

enum class CommentSort {
    @JsonProperty("created_at_asc")
    CreatedAtAsc,

    @JsonProperty("created_at_desc")
    CreatedAtDesc,
}