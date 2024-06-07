package org.example.todolistserverchapter4.api.v1.domain.user.repository

import org.example.todolistserverchapter4.api.v1.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAndPassword(email: String, password: String): User?
}
