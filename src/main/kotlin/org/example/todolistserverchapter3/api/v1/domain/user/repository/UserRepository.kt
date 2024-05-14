package org.example.todolistserverchapter3.api.v1.domain.user.repository

import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>
