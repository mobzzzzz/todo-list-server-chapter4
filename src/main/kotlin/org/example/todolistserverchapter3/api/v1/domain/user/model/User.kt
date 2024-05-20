package org.example.todolistserverchapter3.api.v1.domain.user.model

import jakarta.persistence.*
import org.example.todolistserverchapter3.api.v1.domain.user.dto.SignUpDto
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

    fun updateProfile(profile: Profile) {
        this.profile = profile
    }

    companion object {
        fun createFrom(request: SignUpDto): User {
            return User(
                email = request.email,
                password = request.password,
                profile = Profile(request.nickname)
            )
        }
    }
}