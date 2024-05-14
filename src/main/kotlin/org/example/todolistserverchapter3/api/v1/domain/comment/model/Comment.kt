package org.example.todolistserverchapter3.api.v1.domain.comment.model

import jakarta.persistence.*
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

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
    val createdAt: String,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: String?,

    @Column(name = "deleted_at")
    var deletedAt: String?,

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
