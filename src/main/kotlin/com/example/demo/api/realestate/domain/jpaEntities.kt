package com.example.demo.api.realestate.domain

import com.example.demo.api.common.DomainException
import com.example.demo.jpa.JpaTypes
import com.example.demo.logging.AppLogger
import org.hibernate.annotations.Type
import org.hibernate.validator.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Entity
data class Property(
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

        @Column(name = "type", nullable = false)
        @Enumerated(EnumType.STRING)
        @get:[NotNull]
        var type: PropertyType,

        @get:[NotNull]
        var name: String, // can be blank

        @Embedded
        @get:[NotNull Valid]
        var address: PropertyAddress
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

enum class PropertyType {
    APARTMENT,
    HOUSE
}

@Embeddable
data class PropertyAddress(

        @Column(name = "address_country", nullable = false)
        @get:[NotNull NotBlank]
        val country: String,

        @Column(name = "address_city", nullable = false)
        @get:[NotNull NotBlank]
        val city: String,

        @Column(name = "address_zip", nullable = false)
        @get:[NotNull] // can be blank
        val zip: String,

        @Column(name = "address_street", nullable = false)
        @get:[NotNull] // can be blank
        val street: String,

        @Column(name = "address_number", nullable = false)
        @get:[NotNull] // can be blank
        val number: String,

        @Column(name = "address_district", nullable = false)
        @get:[NotNull] // can be blank
        val district: String,

        @Column(name = "address_neighborhood", nullable = false)
        @get:[NotNull] // can be blank
        val neighborhood: String
)


@Entity
@Table(
        name = "propertylinks",
        uniqueConstraints = arrayOf(
                UniqueConstraint(
                        columnNames = arrayOf("fk_from", "fk_to")
                )
        )

)
data class PropertyLink(
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

        @Column(name = "fk_from", nullable = false)
        @get:[NotNull]
        val fromPropertyId: UUID,

        @Column(name = "fk_to", nullable = false)
        @get:[NotNull]
        val toPropertyId: UUID
) {
    fun getCreatedAt(): Instant = created
    fun getModifiedAt(): Instant = modified

    @PreUpdate @Validated
    private fun beforeUpdate() {
        validateBeforeSave()
        this.modified = Instant.now()
        LOG.info("beforeUpdate $this")
    }

    @PrePersist @Validated
    private fun beforeInsert() {
        validateBeforeSave()
        created = Instant.now()
        modified = Instant.now()
        LOG.info("beforeInsert $this")
    }

    private fun validateBeforeSave() {
        if (fromPropertyId == toPropertyId) {
            throw DomainException(
                    "It makes no sense to link a property to itself!" +
                            "PropertyLink.fromId must not equal PropertyLink.toId !"
            )
        }
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}