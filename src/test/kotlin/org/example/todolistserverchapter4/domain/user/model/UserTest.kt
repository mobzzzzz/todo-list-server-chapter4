package org.example.todolistserverchapter4.domain.user.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.example.todolistserverchapter4.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter4.api.v1.domain.user.model.Profile
import org.example.todolistserverchapter4.api.v1.domain.user.model.User

class UserTest : BehaviorSpec({
    Given("회원 가입 정보 Dto가 주어졌을 때") {
        val signUpDto = SignUpDto(
            email = "test@example.com",
            password = "password",
            nickname = "nickname"
        )

        When("User의 fromDto 메서드로 User를 생성하면") {
            val user = User.fromDto(signUpDto)

            Then("생성된 Entity의 정보는 회원가입 정보와 같아야 한다") {
                user.email shouldBe signUpDto.email
                user.password shouldBe signUpDto.password
                user.profile.nickname shouldBe signUpDto.nickname
            }
        }

        When("User의 프로필을 업데이트하면") {
            val user = User.fromDto(signUpDto)
            val testProfile = Profile(nickname = "newName")

            user.updateProfile(testProfile)

            Then("User의 프로필은 업데이트된 프로필과 같아야 한다") {
                user.profile shouldBe testProfile
            }
        }

        When("User 프로필 내용중 닉네임을 빈칸으로 업데이트하려고 하면") {
            val user = User.fromDto(signUpDto)
            val testProfile = Profile(nickname = "")

            Then("닉네임 길이 불변성 검증에서 예외가 발생해야 한다") {
                shouldThrow<IllegalArgumentException> {
                    user.updateProfile(testProfile)
                }
            }
        }
    }
})