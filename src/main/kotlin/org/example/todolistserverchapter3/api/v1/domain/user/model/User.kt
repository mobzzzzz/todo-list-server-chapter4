package org.example.todolistserverchapter3.api.v1.domain.user.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

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
    val registeredAt: String,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: String,

    @Column(name = "last_signin_at")
    var lastSignInAt: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
