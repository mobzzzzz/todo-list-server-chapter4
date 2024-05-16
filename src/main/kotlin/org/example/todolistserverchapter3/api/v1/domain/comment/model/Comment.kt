package org.example.todolistserverchapter3.api.v1.domain.comment.model

import jakarta.persistence.*
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "content")
    var content: String,

    // 익명의 사용자를 Comment 에서 분리하는 획기적인 방법?
    @Column(name = "name")
    var name: String?,

    @Column(name = "password")
    var password: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: CommentStatus,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime?,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comment.toDto(): CommentDto {
    return CommentDto(
        id = this.id!!,
        todoId = this.todo.id!!,
        userId = this.user?.id,
        content = this.content,
        name = this.user?.profile?.nickname ?: this.name ?: "",
        status = this.status.name,
        createdAt = this.createdAt.toString(),
    )
}