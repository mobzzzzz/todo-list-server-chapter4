package org.example.todolistserverchapter4.api.v1.infra.security

import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {
    fun getCurrentUserIdOrNull(): Long? {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as? UserPrincipal
        return principal?.id
    }
}