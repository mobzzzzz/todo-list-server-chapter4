package org.example.todolistserverchapter3.api.v1.domain.todo.model

import jakarta.persistence.*
import org.example.todolistserverchapter3.api.v1.domain.todo.dto.TodoCreateDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.status.TodoCardStatus
import org.example.todolistserverchapter3.api.v1.domain.todo.model.status.TodoStatus
import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.UpdateTimestamp
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

    @Column(name = "user_id")
    val userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    private fun validate() {
        require(title.isNotBlank()) { "Title cannot be blank" }
        require(title.length <= 100) { "Title must be 100 characters or less" }
        require(this.description != null && this.description!!.length <= 1000) { "Description must be 1000 characters or less" }
    }

    companion object {
        fun fromDto(request: TodoCreateDto, userId: Long): Todo {
            return Todo(
                title = request.title,
                description = request.description,
                userId = userId
            ).apply { this.validate() }
        }
    }
}