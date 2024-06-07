package org.example.todolistserverchapter4.api.v1.domain.user.service

import org.example.todolistserverchapter4.api.v1.domain.user.dto.*

interface UserService {

    /**
     * 회원가입을 진행합니다.
     *
     * @param 회원가입 요청 정보
     * @return 회원가입한 유저 정보
     */
    fun signUp(request: SignUpDto): UserDto

    /**
     * 로그인을 진행합니다.
     *
     * @param 로그인 요청 정보
     * @return 로그인한 유저 정보
     */
    fun signIn(request: SignInDto): SignInResponseDto

    /**
     * 유저의 프로필 정보를 조회합니다.
     *
     * @param 조회할 유저 ID
     * @return 유저의 프로필 정보
     */
    fun getUserProfile(userId: Long): UserDto

    fun getUserProfiles(userIds: List<Long>): List<UserDto>

    /**
     * 유저의 프로필 정보를 수정합니다.
     *
     * @param userId: 수정할 유저 ID
     * @param request: 수정할 프로필 정보
     * @return 수정된 유저의 프로필 정보
     */
    fun updateUserProfile(userId: Long, request: UserUpdateProfileDto): UserDto

    /**
     * 회원 탈퇴를 진행합니다.
     *
     * @param 탈퇴할 유저 ID
     */
    fun deactivate(userId: Long)
}
