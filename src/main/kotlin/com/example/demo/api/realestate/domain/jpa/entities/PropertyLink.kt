package com.example.demo.api.realestate.domain.jpa.entities

import com.example.demo.api.common.DomainException
import com.example.demo.jpa.JpaTypes
import com.example.demo.logging.AppLogger
import org.hibernate.annotations.Type
import org.springframework.validation.annotation.Validated
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

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
