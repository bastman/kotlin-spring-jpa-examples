package com.example.demo.jpa

import com.example.demo.logging.AppLogger
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.validation.annotation.Validated
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct
import javax.persistence.*
import javax.validation.constraints.Size
import kotlin.jvm.Transient


/*
class AuditListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private fun beforeAnyOperation(o:Any?) {
        println(o)
    }

}
*/

class MyListener() {
    @PrePersist
    @PreUpdate
    @PreRemove
    fun foo(o:Any?) {
        println("foo!!!")
    }
}

@Entity
@EntityListeners(MyListener::class)
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

    fun getCreatedAt():Instant = createdAt
    fun getModifiedAt():Instant = modifiedAt



    // (1) hibernate: postLoad
    // (2) kotlin: init just called if you ... newEntity = old.copy()
    // (3) hibernate: preUpdate
    init {
        // not executed by hibernate
        LOG.info("init $this")
    }

    @PostLoad
    fun postLoad() {
        LOG.info("postLoad $this")
    }

    @PreUpdate
    @Validated
    fun beforeUpdate() {
        LOG.info("preUpdate $this")
        this.modifiedAt = Instant.now()
        LOG.info("preUpdate. done: $this")
    }


    @PostConstruct
    fun postConstruct() {
        // never gets called
        LOG.info("postConstruct $this")
    }

    @PrePersist
    @Validated
    fun beforeInsert() {
        // before insert
        LOG.info("preInsert $this")
        createdAt = Instant.now()
        modifiedAt = Instant.now()
        LOG.info("preInsert. done: $this")
    }

    @PostPersist
    fun postPersist() {
        // after insert
        LOG.info("postPersist $this")
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}

@Entity
data class Tweet(
        @Id
        @Type(type = JpaTypes.UUID)
        val id: UUID,
        @Version
        val version: Int = -1,
        @Column(name = "created_at", nullable = false)
        val createdAt: Instant,
        @Column(name = "modified_at", nullable = false)
        val modifiedAt: Instant,

        @ManyToOne
        @JoinColumn(name = "author.id", nullable = false)
        val author: Author,

        @Column(name = "message", nullable = false)
        val message: String
)