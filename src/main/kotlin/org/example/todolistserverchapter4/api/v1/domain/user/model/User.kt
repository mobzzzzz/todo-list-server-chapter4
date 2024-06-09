package org.example.todolistserverchapter4.api.v1.domain.user.model

import jakarta.persistence.*
import org.example.todolistserverchapter4.api.v1.domain.user.dto.SignUpDto
import org.example.todolistserverchapter4.api.v1.oauth.client.dto.OAuth2UserInfo
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "app_user")
class User(
    @Column(unique = true, name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole = UserRole.User,

    @Column(name = "provider")
    var provider: String? = null,

    @Column(name = "provider_id")
    var providerId: String? = null,

    @CreationTimestamp
    @Column(name = "registered_at")
    val registeredAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "last_signin_at")
    var lastSignInAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateProfile(profile: Profile) {
        this.profile = profile
    }

    private fun validate() {
        require(email.isNotBlank()) { "Email cannot be blank" }
        require(profile.nickname.isNotBlank()) { "Nickname cannot be blank" }
        require(profile.nickname.length in 2..10) { "Nickname must be between 2 and 10 characters" }
    }

    companion object {
        fun fromDto(request: SignUpDto): User {
            return User(
                email = request.email,
                password = request.password,
                profile = Profile(request.nickname)
            ).apply { this.validate() }
        }

        fun fromOauth(info: OAuth2UserInfo, randomPassword: String): User {
            return User(
                email = info.email,
                password = randomPassword,
                profile = Profile(info.nickname),
                provider = info.provider.name,
                providerId = info.id
            )
        }
    }
}