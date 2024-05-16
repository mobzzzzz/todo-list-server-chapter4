package org.example.todolistserverchapter3.api.v1.domain.todo.model

import jakarta.persistence.*
import jakarta.persistence.Table
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoDto
import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.hibernate.annotations.*
import java.time.LocalDateTime

@Entity
@Table(name = "todo")
@SQLRestriction("status != 'Deleted'")
@SQLDelete(sql = "UPDATE todo SET status = 'Deleted', deleted_at = NOW() WHERE id = ?")
class Todo(
    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: TodoStatus = TodoStatus.Alive,

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status")
    var cardStatus: TodoCardStatus = TodoCardStatus.NotStarted,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Todo.toDto(): TodoDto {
    return TodoDto(
        id = this.id!!,
        userId = this.user.id!!,
        userName = this.user.profile.nickname,
        title = this.title,
        description = this.description,
        status = this.status.name,
        cardStatus = this.cardStatus.name,
        createdAt = this.createdAt.toString()
    )
}