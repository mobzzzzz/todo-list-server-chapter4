package org.example.todolistserverchapter4.api.v1.domain.user.model

import jakarta.persistence.*
import org.example.todolistserverchapter4.api.v1.domain.user.dto.SignUpDto
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
        require(password.length in 8..20) { "Password must be between 8 and 20 characters" }
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
    }
}