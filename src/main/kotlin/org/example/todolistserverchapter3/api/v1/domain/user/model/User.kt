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
    var role: UserRole,

    @CreationTimestamp
    @Column(name = "registered_at")
    val registeredAt: LocalDateTime,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime?,

    @Column(name = "last_signin_at")
    var lastSignInAt: LocalDateTime?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id!!,
        nickname = this.profile.nickname,
        email = this.email,
        role = this.role.name,
    )
}