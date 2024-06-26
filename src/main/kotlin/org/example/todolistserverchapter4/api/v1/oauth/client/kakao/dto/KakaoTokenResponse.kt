package org.example.todolistserverchapter4.api.v1.oauth.client.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
)
