package org.example.todolistserverchapter3.api.v1.domain.user.model

import jakarta.persistence.*
import org.example.todolistserverchapter3.api.v1.domain.user.dto.UserDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "app_user")
class User(
    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole = UserRole.User,

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

    fun updateProfile(nickname: String) {
        this.profile = Profile(nickname = nickname)
    }
}

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id!!,
        nickname = this.profile.nickname,
        email = this.email,
        role = this.role.name,
    )
}