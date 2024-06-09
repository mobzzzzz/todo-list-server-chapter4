package org.example.todolistserverchapter4.api.v1.oauth.service

import org.example.todolistserverchapter4.api.v1.domain.user.service.UserService
import org.example.todolistserverchapter4.api.v1.infra.security.jwt.JwtPlugin
import org.example.todolistserverchapter4.api.v1.oauth.client.OAuth2ClientService
import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider
import org.springframework.stereotype.Service

@Service
class OAuth2LoginService(
    private val oauth2Client: OAuth2ClientService,
    private val userService: UserService,
    private val jwtPlugin: JwtPlugin
) {

    fun login(provider: OAuth2Provider, authorizationCode: String): String {
        return oauth2Client.login(provider, authorizationCode)
            .let { userService.registerIfAbsent(it) }
            .let {
                jwtPlugin.generateAccessToken(
                    subject = it.id.toString(),
                    role = it.role
                )
            }
    }
}
