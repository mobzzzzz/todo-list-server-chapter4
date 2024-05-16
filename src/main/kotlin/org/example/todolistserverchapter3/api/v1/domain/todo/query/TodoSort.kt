package org.example.todolistserverchapter3.api.v1.domain.todo.query

import com.fasterxml.jackson.annotation.JsonProperty

enum class TodoSort {
    @JsonProperty("created_at_desc")
    CREATED_AT_DESC,

    @JsonProperty("created_at_asc")
    CREATED_AT_ASC,
}