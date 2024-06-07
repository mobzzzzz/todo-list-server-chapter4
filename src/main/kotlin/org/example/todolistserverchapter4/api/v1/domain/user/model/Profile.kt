package org.example.todolistserverchapter4.api.v1.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Profile(
    @Column(name = "nickname")
    val nickname: String
)