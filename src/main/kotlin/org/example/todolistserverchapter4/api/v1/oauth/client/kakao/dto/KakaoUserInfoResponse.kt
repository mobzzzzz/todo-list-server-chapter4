package org.example.todolistserverchapter4.api.v1.oauth.client.kakao.dto

import org.example.todolistserverchapter4.api.v1.oauth.client.dto.OAuth2UserInfo
import org.example.todolistserverchapter4.api.v1.oauth.common.OAuth2Provider

class KakaoUserInfoResponse(
    id: Long,
    properties: KakaoUserInfoPropertiesResponse
) : OAuth2UserInfo(
    provider = OAuth2Provider.Kakao,
    id = id.toString(),
    nickname = properties.nickname,
    email = ""
)
