package org.example.todolistserverchapter4.api.v1.oauth.client.dto

import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider

open class OAuth2UserInfo(
    val provider: OAuth2Provider,
    val id: String,
    val email: String,
    val nickname: String
)