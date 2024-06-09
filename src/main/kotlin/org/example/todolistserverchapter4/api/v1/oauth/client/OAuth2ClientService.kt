package org.example.todolistserverchapter4.api.v1.oauth.client

import jakarta.transaction.NotSupportedException
import org.example.todolistserverchapter4.api.v1.oauth.client.dto.OAuth2UserInfo
import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider
import org.springframework.stereotype.Service

@Service
class OAuth2ClientService(
    private val clients: List<OAuth2Client>
) {
    fun redirectUrlBy(provider: OAuth2Provider): String {
        val client = this.selectClient(provider)
        return client.redirectUrl()
    }

    fun login(provider: OAuth2Provider, authorizationCode: String): OAuth2UserInfo {
        val client = this.selectClient(provider)
        return client.getAccessToken(authorizationCode)
            .let { client.getUserInfo(it) }
    }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client {
        return clients.find { it.supports(provider) } ?: throw NotSupportedException("지원하지 않는 OAuth Provider 입니다.")
    }
}