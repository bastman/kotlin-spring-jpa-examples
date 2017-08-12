package com.example.demo.api.tweeter.domain

import com.example.demo.jpa.JpaTypes
import com.example.demo.logging.AppLogger
import org.hibernate.annotations.Type
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@EntityListeners(MyAuditListener::class)
data class Author(
        @Id
        @Type(type = JpaTypes.UUID)
        val id: UUID,
        @Version
        val version: Int = -1,
        @Column(name = "created_at", nullable = false)
        private var createdAt: Instant,
        @Column(name = "modified_at", nullable = false)
        private var modifiedAt: Instant,

        @Column(name = "email", nullable = false)
        @get: [NotBlank Email]
        val email: String,
        @Column(name = "first_name", nullable = false)
        @get:[NotBlank Size(min = 3, max = 40)]
        val firstName: String,
        @Column(name = "last_name", nullable = false)
        @get:[NotBlank Size(min = 3, max = 40)]
        val lastName: String
) {

    fun getCreatedAt(): Instant = createdAt
    fun getModifiedAt(): Instant = modifiedAt

    @PostLoad
    private fun postLoad() {
        LOG.info("postLoad $this")
    }

    @PreUpdate @Validated
    private fun beforeUpdate() {
        this.modifiedAt = Instant.now()
        LOG.info("beforeUpdate $this")
    }

    @PrePersist @Validated
    private fun beforeInsert() {
        createdAt = Instant.now()
        modifiedAt = Instant.now()
        LOG.info("beforeInsert $this")
    }

    @PostPersist
    private fun afterInsert() {
        LOG.info("afterInsert $this")
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}

@Entity
@EntityListeners(MyAuditListener::class)
data class Tweet(
        @Id
        @Type(type = JpaTypes.UUID)
        val id: UUID,
        @Version
        val version: Int = -1,
        @Column(name = "created_at", nullable = false)
        private var createdAt: Instant,
        @Column(name = "modified_at", nullable = false)
        private var modifiedAt: Instant,

        @ManyToOne
        @JoinColumn(name = "author.id", nullable = false)
        val author: Author,

        @Column(name = "message", nullable = false)
        val message: String
) {

    fun getCreatedAt(): Instant = createdAt
    fun getModifiedAt(): Instant = modifiedAt

    @PostLoad
    private fun postLoad() {
        LOG.info("postLoad $this")
    }

    @PreUpdate @Validated
    private fun beforeUpdate() {
        this.modifiedAt = Instant.now()
        LOG.info("beforeUpdate $this")
    }

    @PrePersist @Validated
    private fun beforeInsert() {
        createdAt = Instant.now()
        modifiedAt = Instant.now()
        LOG.info("beforeInsert $this")
    }

    @PostPersist
    private fun afterInsert() {
        LOG.info("afterInsert $this")
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}