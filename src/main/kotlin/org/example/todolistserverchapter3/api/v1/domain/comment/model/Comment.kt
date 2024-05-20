package org.example.todolistserverchapter3.api.v1.domain.comment.model

import jakarta.persistence.*
import jakarta.persistence.Table
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithNamePasswordDto
import org.example.todolistserverchapter3.api.v1.domain.comment.dto.CommentCreateWithUserDto
import org.example.todolistserverchapter3.api.v1.domain.todo.model.Todo
import org.example.todolistserverchapter3.api.v1.domain.user.model.User
import org.hibernate.annotations.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
@SQLDelete(sql = "UPDATE comment SET status = 'Deleted', deleted_at = NOW() WHERE id = ?")
@SQLRestriction("status != 'Deleted'")
class Comment(
    @Column(name = "content")
    var content: String,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: CommentStatus = CommentStatus.Alive,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun isOwner(password: String): Boolean {
        return this.password == password
    }

    fun isOwner(user: User): Boolean {
        return this.user == user
    }

    fun getUserName() = this.user?.profile?.nickname ?: this.name ?: ""

    companion object {
        fun createFrom(request: CommentCreateWithUserDto, todo: Todo, user: User?): Comment {
            return Comment(
                content = request.content,
                todo = todo,
                user = user
            )
        }

        fun createFrom(request: CommentCreateWithNamePasswordDto, todo: Todo): Comment {
            return Comment(
                content = request.content,
                name = request.name,
                password = request.password,
                todo = todo
            )
        }
    }
}