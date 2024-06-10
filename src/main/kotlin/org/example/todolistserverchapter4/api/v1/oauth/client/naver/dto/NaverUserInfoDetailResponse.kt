package org.example.todolistserverchapter4.api.v1.oauth.client.naver.dto

data class NaverUserInfoDetailResponse(
    val id: String,
    val nickname: String,
    val email: String,
    val name: String
)
