package org.example.todolistserverchapter4.api.v1.oauth.client.naver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.example.todolistserverchapter4.api.v1.oauth.client.dto.OAuth2UserInfo
import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider

class NaverUserInfoResponse(
    @JsonProperty("resultcode")
    val resultCode: String,
    @JsonProperty("message")
    val message: String,
    @JsonProperty("response")
    response: NaverUserInfoDetailResponse
) : OAuth2UserInfo(
    provider = OAuth2Provider.Naver,
    id = response.id,
    nickname = response.nickname,
    email = response.email
)