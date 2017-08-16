package com.example.demo.api.realestate.domain.jpa.entities

import com.example.demo.jpa.JpaTypes
import com.example.demo.logging.AppLogger
import org.hibernate.annotations.Type
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Broker(
        @Id @Type(type = JpaTypes.UUID)
        @Column(name = "id", nullable = false)
        @get: [NotNull]
        val id: UUID,

        @Version
        @Column(name = "version", nullable = false)
        @get: [NotNull]
        val version: Int = -1,

        @Column(name = "created_at", nullable = false)
        private var created: Instant,
        @Column(name = "modified_at", nullable = false)
        private var modified: Instant,

        @Column(name = "company_name", nullable = false)
        @get:[NotNull NotBlank]
        val companyName: String,

        @Column(name = "first_name", nullable = false)
        @get:[NotNull NotBlank]
        val firstName: String,

        @Column(name = "last_name", nullable = false)
        @get:[NotNull NotBlank]
        val lastName: String,

        @Column(name = "email", nullable = false)
        @get:[NotNull Email]
        val email: String,

        @Column(name = "phone_number", nullable = false)
        @get:[NotNull] // can be blank
        val phoneNumber: String,

        @Column(name = "comment", nullable = false)
        @get:[NotNull] // can be blank
        val comment: String,

        @Embedded
        @get:[NotNull]
        val address: BrokerAddress
) {
    fun getCreatedAt(): Instant = created
    fun getModifiedAt(): Instant = modified

    @PreUpdate @Validated
    private fun beforeUpdate() {
        this.modified = Instant.now()
        LOG.info("beforeUpdate $this")
    }

    @PrePersist @Validated
    private fun beforeInsert() {
        created = Instant.now()
        modified = Instant.now()
        LOG.info("beforeInsert $this")
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}

@Embeddable
data class BrokerAddress(
        @Column(name = "address_country", nullable = false)
        @get:[NotNull]
        val country: String,

        @Column(name = "address_state", nullable = false)
        @get:[NotNull]
        val state: String,

        @Column(name = "address_city", nullable = false)
        @get:[NotNull]
        val city: String,

        @Column(name = "address_street", nullable = false)
        @get:[NotNull]
        val street: String,

        @Column(name = "address_number", nullable = false)
        @get:[NotNull]
        val number: String
) {
    companion object {
        val EMPTY = BrokerAddress(country = "", state = "", city = "", street = "", number = "")
    }
}