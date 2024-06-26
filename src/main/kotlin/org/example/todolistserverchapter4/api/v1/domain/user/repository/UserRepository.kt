package org.example.todolistserverchapter4.api.v1.domain.user.repository

import org.example.todolistserverchapter4.api.v1.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByProviderAndProviderId(provider: String, id: String): User?

    fun existsByEmail(email: String): Boolean
}
