package com.example.demo.jpa

import org.hibernate.annotations.Type
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class Author(
        @Id
        // @Type(type = "uuid-char")
        @Column(name = "id", columnDefinition = "BINARY(16)")
        val id: UUID,
        @Version
        val version: Int = -1,
        @Column(name = "created_at")
        val createdAt: Instant,
        @Column(name = "modified_at")
        val modifiedAt: Instant,

        @Column(name = "email")
        val email: String,
        @Column(name = "first_name")
        val firstName: String,
        @Column(name = "last_name")
        val lastName: String
)

@Entity
data class Tweet(
        @Id
        @Type(type = "uuid-char")
        val id: UUID,
        @Version
        val version: Int,
        @Column(name = "created_at")
        val createdAt: Instant,
        @Column(name = "modified_at")
        val modifiedAt: Instant,

        @ManyToOne
        @JoinColumn(name = "author")
        val author: Author,

        @Column(name = "message")
        val message: String
)