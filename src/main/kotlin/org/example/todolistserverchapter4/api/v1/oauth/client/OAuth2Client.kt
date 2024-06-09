package org.example.todolistserverchapter4.api.v1.oauth.client

import org.example.todolistserverchapter4.api.v1.oauth.client.dto.OAuth2UserInfo
import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider

interface OAuth2Client {
    fun redirectUrl(): String
    fun getAccessToken(code: String): String
    fun getUserInfo(accessToken: String): OAuth2UserInfo
    fun supports(provider: OAuth2Provider): Boolean
}