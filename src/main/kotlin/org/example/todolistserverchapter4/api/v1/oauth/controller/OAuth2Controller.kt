package org.example.todolistserverchapter4.api.v1.oauth.controller

import jakarta.servlet.http.HttpServletResponse
import org.example.todolistserverchapter4.api.v1.oauth.client.OAuth2ClientService
import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider
import org.example.todolistserverchapter4.api.v1.oauth.service.OAuth2LoginService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuth2LoginController(
    private val oauth2LoginService: OAuth2LoginService,
    private val oauth2ClientService: OAuth2ClientService
) {

    @GetMapping("/oauth2/{provider}/login")
    fun redirectLoginPage(@PathVariable provider: OAuth2Provider, response: HttpServletResponse) {
        oauth2ClientService.redirectUrlBy(provider)
            .let { response.sendRedirect(it) }
    }

    @GetMapping("/oauth2/{provider}/callback")
    fun callback(
        @PathVariable provider: OAuth2Provider,
        @RequestParam(name = "code") code: String
    ): String {
        return oauth2LoginService.login(provider, code)
    }
}